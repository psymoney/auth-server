package com.psymoney.authserver.user.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class User {
    private final String username;
    private final String password;
    private final boolean enabled;
    private final List<String> authorities;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.authorities = List.of("user");
    }
}
