package com.jandernery.jtech.services;

import com.jandernery.jtech.app.services.UserService;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void shouldCallServiceMethod() {
        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(mock(UserEntity.class)));

        userService.getByEmail("test@email.com");
    }
}

