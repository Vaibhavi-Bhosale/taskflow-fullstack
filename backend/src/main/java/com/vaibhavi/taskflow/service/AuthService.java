package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.dto.LoginRequest;
import com.vaibhavi.taskflow.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(
                loginRequest.getEmail()
        );

        return new LoginResponse(
                jwtToken,
                "Bearer",
                loginRequest.getEmail()
        );
    }
}