package com.jandernery.jtech.app.dtos;

import java.util.UUID;

public record AuthResponseDTO(
        UUID id,
        String name,
        String email,
        String token
) {
}
