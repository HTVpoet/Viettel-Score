package com.example.viettelscorecore.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
public class Users extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;
}
