package com.psymoney.authserver.user.adapter.out.persistence;

import com.psymoney.authserver.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUserMapper {
    private final UserMapper userMapper = new UserMapper();

    @Test
    void testMapToEntitySuccess() {
        User givenUser = new User("testUsername", "testPassword");

        UserEntity userEntity = userMapper.mapToEntity(givenUser);

        assertThat(userEntity.getUsername()).isEqualTo(givenUser.getUsername());
        assertThat(userEntity.getPassword()).isEqualTo(givenUser.getPassword());
        assertThat(userEntity.isEnabled()).isEqualTo(givenUser.isEnabled());
        assertThat(userEntity.getAuthorities()).hasSize(1);
        assertThat(userEntity.getAuthorities().get(0).getAuthority()).isEqualTo("user");
    }
}
