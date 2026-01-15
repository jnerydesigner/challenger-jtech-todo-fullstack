package com.jandernery.jtech.app.dtos.tasks;

import java.util.List;
import java.util.UUID;

public record BuildUserDTO(
        UUID id,
        String name,
        String email,
        List<BuildTaskDTO> tasks
) {
}
