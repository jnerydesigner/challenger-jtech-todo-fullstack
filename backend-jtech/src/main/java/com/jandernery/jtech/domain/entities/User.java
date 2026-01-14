package com.jandernery.jtech.domain.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;


    @Column(unique = true)
    private String email;

    private String password;
}
