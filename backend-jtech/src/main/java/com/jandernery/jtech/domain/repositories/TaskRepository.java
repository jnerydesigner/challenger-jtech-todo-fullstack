package com.jandernery.jtech.domain.repositories;

import com.jandernery.jtech.app.dtos.tasks.BuildUserDTO;
import com.jandernery.jtech.domain.entities.TaskEntity;

import java.util.UUID;

public interface TaskRepository {
    TaskEntity createTask(UUID userId, String title, String description);

    BuildUserDTO findAllTaskList(UUID userId);

    BuildUserDTO updateTaskStatus(UUID taskId, UUID userId);
}
