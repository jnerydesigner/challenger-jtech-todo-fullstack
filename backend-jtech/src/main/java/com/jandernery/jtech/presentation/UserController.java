package com.jandernery.jtech.presentation;

import com.jandernery.jtech.app.auth.UserMeResponseDTO;
import com.jandernery.jtech.app.dtos.CreateUserDTO;
import com.jandernery.jtech.app.dtos.ResponseUserDTO;
import com.jandernery.jtech.app.dtos.UpdateUserDTO;
import com.jandernery.jtech.app.services.UserService;
import com.jandernery.jtech.domain.entities.UserEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody CreateUserDTO createUserDTO) {
        UserEntity user = userService.createUser(createUserDTO);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<ResponseUserDTO> getUserByEmail(@RequestParam String email) {
        UserEntity user = userService.getByEmail(email);
        ResponseUserDTO response = new ResponseUserDTO(user.getId(), user.getName(), user.getEmail());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO, @PathVariable UUID id) {
        UserEntity user = userService.updateUser(updateUserDTO, id);
        ResponseUserDTO response = new ResponseUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()

        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info-me")
    public ResponseEntity<UserMeResponseDTO> me(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userService.getByEmail(authentication.getName());

        System.out.println(user.getEmail());

        return ResponseEntity.ok(
                new UserMeResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()));
    }

}
