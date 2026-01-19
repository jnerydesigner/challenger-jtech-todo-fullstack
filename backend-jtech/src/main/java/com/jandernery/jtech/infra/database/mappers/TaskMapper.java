package com.jandernery.jtech.infra.database.mappers;

import com.jandernery.jtech.domain.entities.TaskEntity;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.infra.database.entities.TaskJpaEntity;
import com.jandernery.jtech.infra.database.entities.UserJpaEntity;

import java.util.List;

public class TaskMapper {

    public static TaskJpaEntity toPersistence(TaskEntity taskEntity){
        if(taskEntity == null) return null;
        UserJpaEntity userJpaEntity = UserMapper.toJpa(taskEntity.getUser());

        return TaskJpaEntity.builder()
                .user(userJpaEntity)
                .title(taskEntity.getTitle())
                .build();
    }


    public static TaskEntity toDomain(TaskJpaEntity taskJpaEntity){
        UserEntity userEntity = UserMapper.toDomain(taskJpaEntity.getUser());
        return TaskEntity.builder()
                .id(taskJpaEntity.getId())
                .title(taskJpaEntity.getTitle())
                .user(userEntity)
                .createdAt(taskJpaEntity.getCreatedAt())
                .updatedAt(taskJpaEntity.getUpdatedAt())
                .build();
    }

    private static <T> List<T> safeList(List<T> list) {
        return list == null ? List.of() : list;
    }
}
