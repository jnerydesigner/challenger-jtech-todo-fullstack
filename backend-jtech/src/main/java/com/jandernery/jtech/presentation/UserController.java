package com.jandernery.jtech.presentation;

import com.jandernery.jtech.app.dtos.CreateUserDTO;
import com.jandernery.jtech.app.services.UserService;
import com.jandernery.jtech.domain.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
