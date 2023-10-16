package com.psymoney.authserver.user.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psymoney.authserver.config.SecurityConfig;
import com.psymoney.authserver.user.application.port.in.RegisterUserUseCase;
import com.psymoney.authserver.user.exception.UserExistException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RegisterUserController.class)
@Import({SecurityConfig.class})
public class TestRegisterUserController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegisterUserUseCase registerUserUseCase;

    private final String URL = "/user/register";

    @Test
    public void testRegisterUserSuccess() throws Exception {
        UserDto userDto = new UserDto("validUserName", "validPassword");

        mockMvc.perform(buildPostRequest(mapDtoToJsonString(userDto)))
                .andExpect(status().isOk());

        then(registerUserUseCase).should(times(1))
                .createNewUser(argThat(command ->
                command.getUsername().equals(userDto.getUsername()) &&
                command.getPassword().equals(userDto.getPassword())));
    }

    @Test
    public void testRegisterUserFailureByUserExistException() throws Exception {
        UserDto userDto = new UserDto("duplicateUser", "duplicateUserP");

        willThrow(new UserExistException(userDto.getUsername()))
                .given(registerUserUseCase).createNewUser(argThat(command ->
                command.getUsername().equals(userDto.getUsername()) &&
                command.getPassword().equals(userDto.getPassword())));

        mockMvc.perform(buildPostRequest(mapDtoToJsonString(userDto)))
                .andExpect(status().isConflict());

        then(registerUserUseCase).should(times(1))
                .createNewUser(argThat(command ->
                command.getUsername().equals(userDto.getUsername()) &&
                command.getPassword().equals(userDto.getPassword())));
    }

    @Test
    public void testRegisterUserFailureWithInvalidUsername() throws Exception {
        UserDto userDto = new UserDto("", "validPassword");
        String jsonData = mapDtoToJsonString(userDto);

        mockMvc.perform(buildPostRequest(jsonData))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResponse().getContentAsString().equals("invalid arguments"));
    }

    @Test
    public void testRegisterUserFailureWithInvalidPassword() throws Exception {
        UserDto userDto = new UserDto("validUsername", "invalid");
        String jsonData = mapDtoToJsonString(userDto);

        mockMvc.perform(buildPostRequest(jsonData))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResponse().getContentAsString().equals("invalid arguments"));
    }

    @Test
    public void testRegisterUserFailureWithInvalidUsernameAndPassword() throws Exception {
        UserDto userDto = new UserDto("", "invalid");
        String jsonData = mapDtoToJsonString(userDto);

        mockMvc.perform(buildPostRequest(jsonData))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResponse().getContentAsString().equals("invalid arguments"));
    }

    @Test
    public void testRegisterUserFailureWithHttpGetMethodRequest() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testRegisterUserFailureWithHttpPutMethodRequest() throws Exception {
        mockMvc.perform(put(URL))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testRegisterUserFailureWithHttpDeleteMethodRequest() throws Exception {
        mockMvc.perform(delete(URL))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void testRegisterUserFailureWithHttpPatchMethodRequest() throws Exception {
        mockMvc.perform(patch(URL))
                .andExpect(status().isMethodNotAllowed());
    }

    @Disabled
    @Test
    public void testRegisterUserFailureWithPlainPassword() throws Exception {
        UserDto userDto = new UserDto("validUsername", "password");
        String jsonData = mapDtoToJsonString(userDto);

        mockMvc.perform(buildPostRequest(jsonData))
                .andExpect(status().isBadRequest());
    }

    private MockHttpServletRequestBuilder buildPostRequest(String jsonData) {
        return post(URL)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData);
    }

    private String mapDtoToJsonString(UserDto dto) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dto);
    }
}
