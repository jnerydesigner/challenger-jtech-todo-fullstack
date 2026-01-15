package com.jandernery.jtech.app.dtos;

import java.util.List;
import java.util.UUID;

public record AuthUserDTO(
        UUID userId,
        String email,
        String name,
        List<String> roles
) {
}
