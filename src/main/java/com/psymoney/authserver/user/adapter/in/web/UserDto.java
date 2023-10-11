package com.psymoney.authserver.user.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    @NotBlank
    @Size(max = 50)
    private final String username;

    @NotBlank
    @Size(min = 8)
    // TODO: add password validation as annotation
    private final String password;
}
