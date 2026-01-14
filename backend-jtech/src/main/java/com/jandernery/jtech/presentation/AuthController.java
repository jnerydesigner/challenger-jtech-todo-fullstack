package com.jandernery.jtech.presentation;

import com.jandernery.jtech.app.dtos.AuthResponseDTO;
import com.jandernery.jtech.app.dtos.LoginDTO;
import com.jandernery.jtech.app.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO login){
        AuthResponseDTO auth = authService.login(login.email(), login.password());

        return ResponseEntity.ok(auth);
    }
}
