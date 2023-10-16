package com.psymoney.authserver.user.adapter.in.web;

import com.psymoney.authserver.user.application.port.in.RegisterUserCommand;
import com.psymoney.authserver.user.application.port.in.RegisterUserUseCase;
import com.psymoney.authserver.user.exception.UserExistException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class RegisterUserController {

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @PostMapping("/user/register")
    ResponseEntity<?> registerUserAccount(@Valid @RequestBody UserDto userDto) throws UserExistException {
        RegisterUserCommand command = new RegisterUserCommand(userDto.getUsername(), userDto.getPassword());
        registerUserUseCase.createNewUser(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("okay");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = "invalid arguments";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<String> handleUserExistException(UserExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
