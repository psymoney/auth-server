package com.psymoney.authserver.user.adapter.out.persistence;

import com.psymoney.authserver.user.application.port.out.RegisterUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements RegisterUserPort {
    @Autowired private UserRepository userRepository;

    @Override
    public boolean isUsernameExisted(String username) {
        return userRepository.existsByUsername(username);
    }
}
