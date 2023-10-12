package com.psymoney.authserver.user.application.port.out;

public interface RegisterUserPort {
    boolean isUsernameExisted(String username);
}
