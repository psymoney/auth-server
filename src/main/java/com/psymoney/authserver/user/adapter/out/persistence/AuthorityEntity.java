package com.psymoney.authserver.user.adapter.out.persistence;

import jakarta.persistence.*;


@Entity
@Table(name = "authorities", indexes = @Index(name = "ix_auth_username", columnList = "username, authority"))
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private UserEntity users;

    @Column(length = 50, nullable = false)
    private String authority;
}
