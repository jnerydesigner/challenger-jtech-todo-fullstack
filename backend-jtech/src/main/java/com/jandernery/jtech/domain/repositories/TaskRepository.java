package com.jandernery.jtech.domain.repositories;

import com.jandernery.jtech.app.dtos.tasks.BuildUserDTO;
import com.jandernery.jtech.domain.entities.TaskEntity;

import java.util.UUID;

public interface TaskRepository {
    BuildUserDTO createTask(UUID userId, String title);

    BuildUserDTO findAllTaskList(UUID userId);

    BuildUserDTO updateTaskStatus(UUID taskId, UUID userId);

    BuildUserDTO updateTaskTitle(UUID taskId, String email, String title);

    BuildUserDTO deleteTask(UUID taskId, String email);

    TaskEntity findTaskByIdAndUser(UUID taskId, String email);
}
