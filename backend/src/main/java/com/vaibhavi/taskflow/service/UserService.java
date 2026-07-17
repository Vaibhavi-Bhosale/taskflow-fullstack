package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.dto.UserRequest;
import com.vaibhavi.taskflow.dto.UserResponse;
import com.vaibhavi.taskflow.entity.User;
import com.vaibhavi.taskflow.enums.UserRole;
import com.vaibhavi.taskflow.exception.EmailAlreadyExistsException;
import com.vaibhavi.taskflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserResponse createUser(UserRequest request) {
         Optional<User> checkUser = userRepository.findByEmail(request.getEmail());

         if(checkUser.isPresent())
         {
             throw new EmailAlreadyExistsException("Email Already Exits...try another one haha");
         }

         User user = new User();

         user.setName(request.getName());
         user.setEmail(request.getEmail());
         user.setPassword(request.getPassword());
         user.setRole(UserRole.USER);

         User newUser = userRepository.save(user);

         UserResponse response =  new UserResponse();

         response.setName(newUser.getName());
         response.setEmail(newUser.getEmail());
         response.setRole(newUser.getRole());
         response.setId(newUser.getId());

         return  response;
    }
}
