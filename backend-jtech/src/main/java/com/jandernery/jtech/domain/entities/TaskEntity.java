package com.jandernery.jtech.domain.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskEntity {
    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private UserEntity user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
