package com.psymoney.authserver.user.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psymoney.authserver.config.SecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RegisterUserController.class)
@Import({SecurityConfig.class})
public class TestRegisterUserController {

    @Autowired
    MockMvc mockMvc;

    private final String URL = "/user/register";

    @Test
    public void testRegisterUserSuccess() throws Exception {
        UserDto userDto = new UserDto("validUserName", "validPassword");
        String jsonData = mapDtoToJsonString(userDto);

        mockMvc.perform(buildPostRequest(jsonData))
                .andExpect(status().isOk());
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
