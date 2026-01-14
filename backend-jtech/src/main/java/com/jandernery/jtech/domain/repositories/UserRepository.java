package com.jandernery.jtech.domain.repositories;


import com.jandernery.jtech.domain.entities.UserEntity;
import java.util.Optional;


public interface UserRepository {
    Optional<UserEntity> findByEmail(String email);
    UserEntity save(UserEntity user);
}
