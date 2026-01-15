package com.jandernery.jtech.infra.database.mappers;

import com.jandernery.jtech.app.dtos.tasks.BuildTaskDTO;
import com.jandernery.jtech.app.dtos.tasks.BuildUserDTO;
import com.jandernery.jtech.domain.entities.TaskEntity;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.infra.database.entities.TaskJpaEntity;
import com.jandernery.jtech.infra.database.entities.UserJpaEntity;

import java.util.List;

public class UserMapper {

    public static UserEntity toDomain(UserJpaEntity entity) {
        if (entity == null) return null;

        return UserEntity.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .password(entity.getPassword())
                .tasks(List.of())
                .build();
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

    public static BuildUserDTO toResponse(UserJpaEntity userJpaEntity){
        return new BuildUserDTO(
                userJpaEntity.getId(),
                userJpaEntity.getName(),
                userJpaEntity.getEmail(),
                userJpaEntity.getTasks().stream()
                        .map(task -> new BuildTaskDTO(
                                task.getId(),
                                task.getTitle(),
                                task.getDescription(),
                                task.isCompleted(),
                                task.getCreatedAt(),
                                task.getUpdatedAt()
                        ))
                        .toList()
        );
    }


}
