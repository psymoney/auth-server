package com.psymoney.authserver.user.adapter.out.persistence;

import com.psymoney.authserver.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
class UserMapper {

    UserEntity mapToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        ArrayList<AuthorityEntity> authorities = new ArrayList<>();

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEnabled(user.isEnabled());

        for (String authority: user.getAuthorities()) {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setUsers(userEntity);
            authorityEntity.setAuthority(authority);
            authorities.add(authorityEntity);
        }

        userEntity.setAuthorities(authorities);
        return userEntity;
    }
}
