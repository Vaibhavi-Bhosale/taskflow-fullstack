package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.dto.LoginRequest;
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

       public String login(LoginRequest loginRequest)
       {
           UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                   loginRequest.getEmail(),
                   loginRequest.getPassword()

           );

           Authentication authentication = authenticationManager.authenticate(token);


           return jwtService.generateToken(loginRequest.getEmail());
       }
}