package com.vaibhavi.taskflow.controller;

import com.vaibhavi.taskflow.dto.TaskResponse;
import com.vaibhavi.taskflow.dto.UserRequest;
import com.vaibhavi.taskflow.dto.UserResponse;
import com.vaibhavi.taskflow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/test")
    public String test() {
        return "Hiii..public route";
    }


    @GetMapping("/user")
    public String user() {
        return "Welcome user !";
    }


    @GetMapping("/admin")
    public String admin() {
        return "for admin only";
    }


    @PostMapping("/users")
    public UserResponse createUser(
            @Valid @RequestBody UserRequest request) {

        return userService.createUser(request);
    }


    @GetMapping("/users/tasks/{userId}")
    public List<TaskResponse> getUsersTasks(
            @PathVariable Long userId) {

        return userService.getUserTasks(userId);
    }
}