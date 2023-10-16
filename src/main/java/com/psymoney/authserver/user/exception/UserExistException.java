package com.psymoney.authserver.user.exception;

public class UserExistException extends Exception {
    public UserExistException(String username) {
        super("Duplicate username: " + username);
    }
}
