package com.jandernery.jtech.app.dtos;

import com.jandernery.jtech.domain.entities.TaskEntity;
import com.jandernery.jtech.domain.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ResponseTaskListDTO(
        UUID id,
        String title,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ResponseUserDTO user
        ) {
}