package com.jandernery.jtech.app.services;

import com.jandernery.jtech.app.dtos.AuthResponseDTO;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.UserRepository;
import com.jandernery.jtech.infra.database.entities.UserJpaEntity;
import com.jandernery.jtech.infra.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthResponseDTO login(String email, String password) {

        UserEntity user = userService.getByEmail(email);

        if (user.getId() == null) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        return issueTokens(user);
    }

    private AuthResponseDTO issueTokens(UserEntity user) {

        Map<String, Object> claims = Map.of(
                "userId", user.getId().toString(),
                "name", user.getName(),
                "email", user.getEmail()
        );

        // 🔑 SUBJECT = EMAIL (ou username)
        String accessToken =
                jwtService.generateToken(user.getEmail(), claims);

        return new AuthResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                accessToken
        );
    }
}
