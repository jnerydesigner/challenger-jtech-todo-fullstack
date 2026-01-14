package com.jandernery.jtech.app.services;

import com.jandernery.jtech.app.dtos.CreateUserDTO;
import com.jandernery.jtech.app.dtos.UpdateUserDTO;
import com.jandernery.jtech.domain.entities.UserEntity;
import com.jandernery.jtech.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserEntity createUser(CreateUserDTO createUserDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(createUserDTO.email());
        userEntity.setName(createUserDTO.name());

        String hashedPassword = passwordEncoder.encode(createUserDTO.password());
        userEntity.setPassword(hashedPassword);

        return userRepository.createUser(userEntity);
    }

    public UserEntity getByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserEntity updateUser(UpdateUserDTO updateUserDTO, UUID id){
        return userRepository.updateUser(id, updateUserDTO.name());
    }

    public boolean userExistsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}
