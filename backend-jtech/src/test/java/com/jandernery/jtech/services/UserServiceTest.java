package com.jandernery.jtech.services;

import com.jandernery.jtech.app.dtos.CreateUserDTO;
import com.jandernery.jtech.app.services.UserService;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    void shouldCallServiceMethod() {
        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mock(UserEntity.class)));

        userService.getByEmail("test@email.com");
    }

    @Test
    void shouldCreateUserSuccessfully(){
        CreateUserDTO dto = new CreateUserDTO(
                "John Doe",
                "john.doe@test.com",
                "123456"
        );

        UserEntity savedUser = new UserEntity();
        savedUser.setId(UUID.fromString("ae7847d4-8984-49db-971f-871ab3c3698c"));
        savedUser.setName(dto.name());
        savedUser.setEmail(dto.email());
        savedUser.setPassword("$2a$10$hashFake");

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);

        UserEntity result = userService.createUser(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(dto.name(), result.getName());
        assertEquals(dto.email(), result.getEmail());
        assertEquals("$2a$10$hashFake", result.getPassword());

        verify(userRepository).save(any(UserEntity.class));

    }
}

