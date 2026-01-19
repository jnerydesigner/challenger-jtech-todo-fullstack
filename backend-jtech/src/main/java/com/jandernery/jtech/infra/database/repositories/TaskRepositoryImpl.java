package com.jandernery.jtech.infra.database.repositories;

import com.jandernery.jtech.app.dtos.tasks.BuildUserDTO;
import com.jandernery.jtech.app.dtos.tasks.UpdateStatusTaskDTO;
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
    public BuildUserDTO createTask(UUID userId, String title) {
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findById(userId);
        if(userJpaEntity.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }
        UserEntity user = UserMapper.toDomain(userJpaEntity.get());
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUser(user);
        taskEntity.setTitle(title);

        TaskJpaEntity taskJpaEntity = TaskMapper.toPersistence(taskEntity);
        TaskJpaEntity taskJpaSave = taskJpaRepository.save(taskJpaEntity);

        Optional<UserJpaEntity> build = userJpaRepository.findByIdWithTasks(userId);

        if(build.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }

        return UserMapper.toResponse(build.get());
    }



    @Override
    public BuildUserDTO findAllTaskList(UUID userId) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<List<TaskJpaEntity>> taskJpaEntities = taskJpaRepository.findByUserId(userId);
        if (taskJpaEntities.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }



        Optional<UserJpaEntity> user = userJpaRepository.findByIdWithTasks(userId);

        if(user.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }

        return UserMapper.toResponse(user.get());
    }

    @Override
    public BuildUserDTO updateTaskStatus(UUID taskId, UUID userId) {
        Optional<UserJpaEntity> user = userJpaRepository.findByIdWithTasks(userId);

        if(user.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }

        Optional<TaskJpaEntity> task = taskJpaRepository.findById(taskId);

        if(task.isEmpty()){
            throw new RuntimeException("Tarefa não encontrada");
        }

        TaskJpaEntity taskEntity = task.get();

        taskEntity.setCompleted(!taskEntity.isCompleted());

        taskJpaRepository.save(task.get());

        return UserMapper.toResponse(user.get());
    }

    @Override
    public BuildUserDTO updateTaskTitle(UUID taskId, String email, String title) {
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findByEmail(email);

        if(userJpaEntity.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }

        Optional<TaskJpaEntity> taskJpaEntity = taskJpaRepository.findById(taskId);

        if(taskJpaEntity.isEmpty()){
            throw new RuntimeException("Tarefa não encontrada");
        }

        taskJpaEntity.get().setTitle(title);
        taskJpaRepository.save(taskJpaEntity.get());

        Optional<UserJpaEntity> user = userJpaRepository.findByIdWithTasks(userJpaEntity.get().getId());

        if(user.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }


        return UserMapper.toResponse(user.get());
    }

    @Override
    @Transactional
    public BuildUserDTO deleteTask(UUID taskId, String email) {
        UserJpaEntity user = findUserJpa(email);

        Optional<TaskJpaEntity> task = taskJpaRepository.findById(taskId);

        if(task.isEmpty()){
            throw new RuntimeException("Tarefa não encontrada");
        }

        taskJpaRepository.delete(task.get());


        return findTasksGeneral(user.getId());
    }

    @Override
    public TaskEntity findTaskByIdAndUser(UUID taskId, String email) {
        UserJpaEntity user = findUserJpa(email);

        Optional<TaskJpaEntity> task = taskJpaRepository.findById(taskId);

        if(task.isEmpty()){
            throw new RuntimeException("Tarefa não encontrada");
        }

        TaskEntity taskEntity = TaskMapper.toDomain(task.get());

        return taskEntity;
    }

    private UserJpaEntity findUserJpa(String email){
        Optional<UserJpaEntity> userJpaEntity = userJpaRepository.findByEmail(email);

        if(userJpaEntity.isEmpty()){
            throw new RuntimeException("Usuário não encontrado");
        }

        return userJpaEntity.get();
    }

    private BuildUserDTO findTasksGeneral(UUID userId){
        Optional<UserJpaEntity> user = userJpaRepository.findByIdWithTasks(userId);

        if(user.isEmpty()){
            throw new RuntimeException("Tarefa não encontrada");
        }

        return UserMapper.toResponse(user.get());
    }
}
