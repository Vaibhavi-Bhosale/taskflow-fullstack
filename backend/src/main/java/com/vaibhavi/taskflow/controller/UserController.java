package com.vaibhavi.taskflow.controller;

import com.vaibhavi.taskflow.dto.TaskResponse;
import com.vaibhavi.taskflow.dto.UserRequest;
import com.vaibhavi.taskflow.dto.UserResponse;
import com.vaibhavi.taskflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/test")
    String test()
    {
        return "Hiii..public route";
    }

    @GetMapping("/admin")
    String admin()
    {
        return "for admin only";
    }

    @PostMapping("/createUser")
    UserResponse createUser(@RequestBody UserRequest request)
    {
        return  userService.createUser(request);
    }

    @GetMapping("/users/tasks/{userId}")
    List<TaskResponse> getUsersTasks(@PathVariable Long userId)
    {
        return  userService.getUserTasks(userId);
    }
}
