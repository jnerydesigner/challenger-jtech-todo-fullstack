package com.jandernery.jtech.app.auth;

import java.util.UUID;

public record UserMeResponseDTO(UUID id, String name, String email) {

}
