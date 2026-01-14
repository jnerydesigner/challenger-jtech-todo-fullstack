package com.jandernery.jtech.infra.database.repositories;


import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.UserRepository;
import com.jandernery.jtech.infra.database.jpa.UserJpaRepository;
import com.jandernery.jtech.infra.database.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userJpaRepository
                .findByEmail(email)
                .map(UserMapper::toDomain);
    }
}
