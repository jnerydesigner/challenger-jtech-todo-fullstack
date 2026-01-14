package com.jandernery.jtech.app.dtos;

import java.util.UUID;

public record ResponseUserDTO(UUID id, String name, String email) {
}
