package com.jandernery.jtech.infra.database.repositories;


import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.UserRepository;
import com.jandernery.jtech.infra.database.entities.UserJpaEntity;
import com.jandernery.jtech.infra.database.jpa.UserJpaRepository;
import com.jandernery.jtech.infra.database.mappers.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserEntity findByEmail(String email) {
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository
                .findByEmail(email);
        if(userJpaEntity.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }

        return UserMapper.toDomain(userJpaEntity.get());
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        UserJpaEntity userJpaEntity = UserMapper.toJpa(user);
        UserJpaEntity userSave = userJpaRepository.save(userJpaEntity);
        return UserMapper.toDomain(userSave);
    }

    @Override
    public UserEntity findById(UUID id) {
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findById(id);
        if(userJpaEntity.isEmpty()){
            throw new RuntimeException("User not found");
        }

        return UserMapper.toDomain(userJpaEntity.get());
    }


    @Override
    public UserEntity updateUser(UUID id, String name){
        UserEntity userEntity = this.findById(id);
        userEntity.setName(name);

        UserJpaEntity userJpaEntity = UserMapper.toJpa(userEntity);

        UserJpaEntity userSaved = userJpaRepository.save(userJpaEntity);

        return UserMapper.toDomain(userSaved);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
