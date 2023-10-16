package com.psymoney.authserver.user.application.port.out;

import com.psymoney.authserver.user.domain.User;

public interface RegisterUserPort {
    boolean isUsernameExisted(String username);
    void saveUser(User user);
}
