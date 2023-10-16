package com.psymoney.authserver.user.application.port.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterUserCommand {

    private final String username;
    private final String password;

    public RegisterUserCommand(@Valid @NotNull @Max(50) String username,
                               @Valid @NotNull @Min(8) String password) {
        this.username = username;
        this.password = password;
    }
}

