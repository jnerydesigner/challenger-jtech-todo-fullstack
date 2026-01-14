package com.jandernery.jtech.infra.database.mappers;

import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.infra.database.entities.UserJpaEntity;

public class UserMapper {

    public static UserEntity toDomain(UserJpaEntity entity) {
        if (entity == null) return null;

        return new UserEntity(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword()
        );
    }

    public static UserJpaEntity toJpa(UserEntity user) {
        if (user == null) return null;

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());

        return entity;
    }
}
