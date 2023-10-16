package com.psymoney.authserver.user.application.port.in;

import com.psymoney.authserver.user.exception.UserExistException;

public interface RegisterUserUseCase {
    public void createNewUser(RegisterUserCommand command) throws UserExistException;
}
