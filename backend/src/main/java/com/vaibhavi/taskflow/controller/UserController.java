package com.vaibhavi.taskflow.controller;

import com.vaibhavi.taskflow.dto.UserRequest;
import com.vaibhavi.taskflow.dto.UserResponse;
import com.vaibhavi.taskflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    UserResponse createUser(@RequestBody UserRequest request)
    {
        return  userService.createUser(request);
    }
}
