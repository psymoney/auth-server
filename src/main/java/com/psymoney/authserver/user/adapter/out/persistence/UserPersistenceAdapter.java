package com.psymoney.authserver.user.adapter.out.persistence;

import com.psymoney.authserver.user.application.port.out.RegisterUserPort;
import com.psymoney.authserver.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class UserPersistenceAdapter implements RegisterUserPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean isUsernameExisted(String username) {
        return userRepository.existsByUsername(username);
    }
}
