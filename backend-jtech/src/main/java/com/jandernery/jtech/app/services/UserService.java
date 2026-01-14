package com.jandernery.jtech.app.services;

import com.jandernery.jtech.app.dtos.CreateUserDTO;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;



    public UserEntity createUser(CreateUserDTO createUserDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(createUserDTO.email());
        userEntity.setName(createUserDTO.name());
        userEntity.setPassword(createUserDTO.password());

        return userRepository.save(userEntity);
    }

    public UserEntity getByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
