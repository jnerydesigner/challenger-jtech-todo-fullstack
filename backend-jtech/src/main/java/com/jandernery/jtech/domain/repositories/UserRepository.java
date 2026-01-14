package com.jandernery.jtech.domain.repositories;


import com.jandernery.jtech.domain.entities.UserEntity;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository {
    Optional<UserEntity> findByEmail(String email);
    UserEntity createUser(UserEntity user);
    UserEntity findById(UUID id);
    UserEntity updateUser(UUID id, String name);
}
