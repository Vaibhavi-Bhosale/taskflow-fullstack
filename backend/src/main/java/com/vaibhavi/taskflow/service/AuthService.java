package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.dto.LoginRequest;
import com.vaibhavi.taskflow.dto.LoginResponse;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
       private final AuthenticationManager authenticationManager;
       private final JwtService jwtService;

       private AuthService(AuthenticationManager authenticationManager,
                           JwtService jwtService)
       {
           this.authenticationManager = authenticationManager;
           this.jwtService = jwtService;
       }

       public LoginResponse login(LoginRequest loginRequest)
       {
           UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                   loginRequest.getEmail(),
                   loginRequest.getPassword()

           );

           Authentication authentication = authenticationManager.authenticate(token);


           String jwtToken =  jwtService.generateToken(loginRequest.getEmail());

           return new LoginResponse(
                   jwtToken,
                   "Bearer",
                   loginRequest.getEmail()

           );
       }
}