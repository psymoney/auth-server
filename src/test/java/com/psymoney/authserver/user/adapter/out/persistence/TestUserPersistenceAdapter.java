package com.psymoney.authserver.user.adapter.out.persistence;

import com.psymoney.authserver.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({UserPersistenceAdapter.class, UserMapper.class})
@Sql(scripts = "classpath:sql/test-user-data-h2.sql")
public class TestUserPersistenceAdapter {
    @Autowired private UserRepository userRepository;
    @Autowired private UserPersistenceAdapter userPersistenceAdapter;
    @Autowired private UserMapper userMapper;

    @Test
    void testLoadedUsers() {
        UserEntity user = userRepository.findByUsername("user");
        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    void testIsUsernameExistedNotFound() {
        boolean isExisted = userPersistenceAdapter.isUsernameExisted("test");

        assertThat(isExisted).isFalse();
    }

    @Test
    void testIsUsernameExistedFound() {
        boolean isExisted = userPersistenceAdapter.isUsernameExisted("user");

        assertThat(isExisted).isTrue();
    }

    @Test
    void testSaveUser() {
        User givenUser = new User("test", "testPassword");
        UserEntity expectedUserEntity = new UserEntity();
        expectedUserEntity.setUsername("test");
        expectedUserEntity.setPassword("testPassword");
        expectedUserEntity.setEnabled(true);

        boolean isSaved = userPersistenceAdapter.saveUser(givenUser);
        UserEntity actualUserEntity = userRepository.findByUsername("test");

        assertThat(isSaved).isTrue();
        assertThat(actualUserEntity.isEnabled()).isEqualTo(expectedUserEntity.isEnabled());
        assertThat(actualUserEntity.getPassword()).isEqualTo(expectedUserEntity.getPassword());
        assertThat(actualUserEntity.getUsername()).isEqualTo(expectedUserEntity.getUsername());
    }
}
