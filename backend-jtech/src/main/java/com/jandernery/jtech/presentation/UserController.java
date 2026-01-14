package com.jandernery.jtech.presentation;

import com.jandernery.jtech.app.dtos.CreateUserDTO;
import com.jandernery.jtech.app.dtos.ResponseUserDTO;
import com.jandernery.jtech.app.dtos.UpdateUserDTO;
import com.jandernery.jtech.app.services.UserService;
import com.jandernery.jtech.domain.entities.UserEntity;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserEntity> create(@RequestBody CreateUserDTO createUserDTO){
        UserEntity user = userService.createUser(createUserDTO);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<ResponseUserDTO> getUserByEmail(@RequestParam String email){
        UserEntity user = userService.getByEmail(email);
        ResponseUserDTO response = new ResponseUserDTO(user.getId(), user.getName(), user.getEmail());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO, @PathVariable UUID id){
        UserEntity user = userService.updateUser(updateUserDTO, id);
        ResponseUserDTO response = new ResponseUserDTO(
                user.getId(),
                user.getName(),
                user.getEmail()

        );

        return ResponseEntity.ok(response);
    }
}
