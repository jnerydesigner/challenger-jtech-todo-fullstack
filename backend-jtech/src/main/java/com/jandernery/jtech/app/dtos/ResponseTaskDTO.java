package com.jandernery.jtech.app.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseTaskDTO(
        UUID id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ResponseUserDTO user
        ) {
}