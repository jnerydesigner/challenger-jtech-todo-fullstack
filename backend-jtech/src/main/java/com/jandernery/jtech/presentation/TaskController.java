package com.jandernery.jtech.presentation;

import com.jandernery.jtech.app.dtos.*;
import com.jandernery.jtech.app.dtos.tasks.BuildUserDTO;
import com.jandernery.jtech.app.dtos.tasks.UpdateStatusTaskDTO;
import com.jandernery.jtech.app.services.TaskService;
import com.jandernery.jtech.app.services.UserService;
import com.jandernery.jtech.app.tasks.TaskChangeTitleDTO;
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
    public ResponseEntity<BuildUserDTO> saveTask(
            @RequestBody CreateTaskDTO createTaskDTO,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        UserEntity userEntity = userService.getByEmail(email);

        BuildUserDTO buildUserDTO = taskService.createTask(createTaskDTO, userEntity.getId());

        return ResponseEntity.ok(buildUserDTO);
    }

    @GetMapping
    public ResponseEntity<BuildUserDTO> listAllTasks(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        return ResponseEntity.ok(taskService.getAllTasks(email));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskEntity> getTaskById(
            @PathVariable UUID taskId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        return ResponseEntity.ok(taskService.getTaskById(taskId, email));
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<BuildUserDTO> updateTask(
            @PathVariable UUID taskId,
            Authentication authentication,
            @RequestBody TaskChangeTitleDTO taskChangeTitleDTO) {
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();

        System.out.println(email);

        BuildUserDTO buildUserDTO = taskService.updateTaskTitle(taskId, email, taskChangeTitleDTO.title());

        return ResponseEntity.ok(buildUserDTO);
    }

    @PatchMapping("/status/{taskId}")
    public ResponseEntity<BuildUserDTO> updateTaskStatus(
            @PathVariable UUID taskId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        UserEntity userEntity = userService.getByEmail(email);

        BuildUserDTO buildUserDTO = taskService.updateTaskStatus(taskId, userEntity.getId());

        return ResponseEntity.ok(buildUserDTO);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<BuildUserDTO> deleteTask(
            @PathVariable UUID taskId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();

        BuildUserDTO buildUserDTO = taskService.deleteTask(email, taskId);

        return ResponseEntity.ok(buildUserDTO);
    }
}
