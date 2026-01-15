package com.jandernery.jtech.app.dtos.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

public record BuildTaskDTO(
        UUID id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}