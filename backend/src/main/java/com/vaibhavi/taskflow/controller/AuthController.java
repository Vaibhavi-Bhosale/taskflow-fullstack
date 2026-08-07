package com.vaibhavi.taskflow.controller;

import com.vaibhavi.taskflow.dto.LoginRequest;
import com.vaibhavi.taskflow.dto.LoginResponse;
import com.vaibhavi.taskflow.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

       private final AuthService authService;
       public AuthController(AuthService authService)
       {
           this.authService = authService;
       }

       @PostMapping("/login")
       public LoginResponse login(@RequestBody LoginRequest loginRequest)
       {
           return  authService.login(loginRequest);
       }
}