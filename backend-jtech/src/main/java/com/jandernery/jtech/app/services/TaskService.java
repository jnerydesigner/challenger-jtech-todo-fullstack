package com.jandernery.jtech.app.services;

import com.jandernery.jtech.app.dtos.CreateTaskDTO;
import com.jandernery.jtech.app.dtos.tasks.BuildUserDTO;
import com.jandernery.jtech.app.dtos.tasks.UpdateStatusTaskDTO;
import com.jandernery.jtech.domain.entities.TaskEntity;
import com.jandernery.jtech.domain.repositories.TaskRepository;
import com.jandernery.jtech.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public BuildUserDTO createTask(CreateTaskDTO createTaskDTO, UUID userId){
        return taskRepository.createTask(userId, createTaskDTO.title());
    }

    public BuildUserDTO getAllTasks(String email) {
        var user = userRepository.findByEmail(email);

        return taskRepository.findAllTaskList(user.getId());
    }

    public BuildUserDTO updateTaskStatus(UUID taskId, UUID userId) {
        return taskRepository.updateTaskStatus(taskId, userId);
    }

    public BuildUserDTO updateTaskTitle(UUID taskId, String email, String title) {
        return taskRepository.updateTaskTitle(taskId, email, title);
    }

    public BuildUserDTO deleteTask(String email, UUID taskId) {
        return taskRepository.deleteTask(taskId, email);
    }
}
