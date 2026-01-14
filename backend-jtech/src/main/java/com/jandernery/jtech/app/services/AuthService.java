package com.jandernery.jtech.app.services;

import com.jandernery.jtech.app.dtos.AuthResponseDTO;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.UserRepository;
import com.jandernery.jtech.infra.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthResponseDTO login(String email, String password){
        boolean exists = userService.userExistsByEmail(email);
        if(!exists){
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        UserEntity user = userService.getByEmail(email);
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        return issueTokens(user);
    }

    private AuthResponseDTO issueTokens(UserEntity user){
        String accessToken = jwtService.generateToken( user.getId().toString(), user);
        return new AuthResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                accessToken
        );
    }
}
