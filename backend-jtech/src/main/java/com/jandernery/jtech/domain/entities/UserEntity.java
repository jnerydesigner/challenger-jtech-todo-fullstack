package com.jandernery.jtech.domain.entities;

import lombok.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<TaskEntity> tasks;
}
