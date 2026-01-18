package com.jandernery.jtech.presentation;

import com.jandernery.jtech.app.dtos.*;
import com.jandernery.jtech.app.dtos.tasks.BuildUserDTO;
import com.jandernery.jtech.app.dtos.tasks.UpdateStatusTaskDTO;
import com.jandernery.jtech.app.services.TaskService;
import com.jandernery.jtech.app.services.UserService;
import com.jandernery.jtech.domain.entities.TaskEntity;
import com.jandernery.jtech.domain.entities.UserEntity;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;

    public TaskController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ResponseTaskDTO> saveTask(
            @RequestBody CreateTaskDTO createTaskDTO,
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        UserEntity userEntity = userService.getByEmail(email);

        TaskEntity taskListEntity = taskService.createTask(createTaskDTO, userEntity.getId());
        ResponseTaskDTO responseTaskDTO = getResponseTaskDTO(taskListEntity);

        return ResponseEntity.ok(responseTaskDTO);
    }

    @GetMapping
    public ResponseEntity<BuildUserDTO> listAllTasks(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        return ResponseEntity.ok(taskService.getAllTasks(user.getUsername()));
    }

    private static ResponseTaskDTO getResponseTaskDTO(TaskEntity taskEntity) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(
                taskEntity.getUser().getId(),
                taskEntity.getUser().getName(),
                taskEntity.getUser().getEmail()
        );
        return new ResponseTaskDTO(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getCreatedAt(),
                taskEntity.getUpdatedAt(),
                responseUserDTO

        );
    }

    @PatchMapping("/status/{taskId}")
    public ResponseEntity<BuildUserDTO> updateTask(
            @PathVariable UUID taskId,
            Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        UserEntity userEntity = userService.getByEmail(email);

        BuildUserDTO buildUserDTO = taskService.updateTaskStatus(taskId, userEntity.getId());

        return ResponseEntity.ok(buildUserDTO);
    }
}
