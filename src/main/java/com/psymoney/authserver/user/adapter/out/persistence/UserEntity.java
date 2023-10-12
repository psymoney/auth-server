package com.psymoney.authserver.user.adapter.out.persistence;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 500, nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AuthorityEntity> authorities;
}
