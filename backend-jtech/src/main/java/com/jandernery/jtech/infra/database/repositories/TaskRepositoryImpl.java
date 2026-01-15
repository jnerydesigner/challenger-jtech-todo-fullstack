package com.jandernery.jtech.infra.database.repositories;

import com.jandernery.jtech.app.dtos.tasks.BuildUserDTO;
import com.jandernery.jtech.domain.entities.TaskEntity;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.TaskRepository;
import com.jandernery.jtech.infra.database.entities.TaskJpaEntity;
import com.jandernery.jtech.infra.database.entities.UserJpaEntity;
import com.jandernery.jtech.infra.database.jpa.TaskJpaRepository;
import com.jandernery.jtech.infra.database.jpa.UserJpaRepository;
import com.jandernery.jtech.infra.database.mappers.TaskMapper;
import com.jandernery.jtech.infra.database.mappers.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final UserJpaRepository userJpaRepository;
    private final TaskJpaRepository taskJpaRepository;

    @Override
    @Transactional
    public TaskEntity createTask(UUID userId, String title, String description) {
        System.out.println(description);
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findById(userId);
        if(userJpaEntity.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }
        UserEntity user = UserMapper.toDomain(userJpaEntity.get());
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUser(user);
        taskEntity.setTitle(title);
        taskEntity.setDescription(description);

        TaskJpaEntity taskJpaEntity = TaskMapper.toPersistence(taskEntity);
        TaskJpaEntity taskJpaSave = taskJpaRepository.save(taskJpaEntity);


        return TaskMapper.toDomain(taskJpaSave);
    }

    @Override
    public BuildUserDTO findAllTaskList(UUID userId) {
        ModelMapper modelMapper = new ModelMapper();
        List<TaskJpaEntity> taskJpaEntities = taskJpaRepository.findByUserId(userId);
        if (taskJpaEntities.isEmpty()) {
            throw new RuntimeException("Usuário não possui listas");
        }



        Optional<UserJpaEntity> user = userJpaRepository.findByIdWithTasks(userId);

        if(user.isEmpty()){
            throw new RuntimeException("Usuário não possui listas");
        }

        return UserMapper.toResponse(user.get());
    }
}
