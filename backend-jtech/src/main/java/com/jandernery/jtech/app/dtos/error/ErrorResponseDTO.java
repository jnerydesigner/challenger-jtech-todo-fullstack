package com.jandernery.jtech.app.dtos.error;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String type, String code, int status, LocalDateTime timestamp) {
}
