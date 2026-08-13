package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.dto.TaskResponse;
import com.vaibhavi.taskflow.dto.UserRequest;
import com.vaibhavi.taskflow.dto.UserResponse;
import com.vaibhavi.taskflow.entity.Task;
import com.vaibhavi.taskflow.entity.User;
import com.vaibhavi.taskflow.enums.UserRole;
import com.vaibhavi.taskflow.exception.EmailAlreadyExistsException;
import com.vaibhavi.taskflow.exception.TaskNotFoundException;
import com.vaibhavi.taskflow.exception.UserNotFoundException;
import com.vaibhavi.taskflow.repository.TaskRepository;
import com.vaibhavi.taskflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {
         Optional<User> checkUser = userRepository.findByEmail(request.getEmail());

         if(checkUser.isPresent())
         {
             throw new EmailAlreadyExistsException("Email Already Exits...try another one haha");
         }

         User user = new User();

         user.setName(request.getName());
         user.setEmail(request.getEmail());

         String passwordBefore = request.getPassword();
         String passwordEncode = passwordEncoder.encode(passwordBefore);
         user.setPassword(passwordEncode);


         user.setRole(UserRole.USER);

         User newUser = userRepository.save(user);

         UserResponse response =  new UserResponse();

         response.setName(newUser.getName());
         response.setEmail(newUser.getEmail());
         response.setRole(newUser.getRole());
         response.setId(newUser.getId());

         return  response;
    }

    public List<TaskResponse> getUserTasks(Long userId) {

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (currentUser.getRole() != UserRole.ADMIN
                && !Objects.equals(userId, currentUser.getId())) {

            throw new RuntimeException("You cannot access another user's tasks");
        }

       Optional<User> optionalUser = userRepository.findById(userId);

       if(optionalUser.isEmpty())
       {
           throw new UserNotFoundException("User not found");
       }

       List<Task> taskList =  taskRepository. findByAssignedToId(userId);
       if(taskList.isEmpty())
       {

           throw new TaskNotFoundException("No Task Found for the user");
       }
       List<TaskResponse> taskResponse = new ArrayList<>(){
       };

       for(Task tasks : taskList)
       {
            TaskResponse taskResponse1 = new TaskResponse();

            taskResponse1.setId(tasks.getId());
            taskResponse1.setTitle(tasks.getTitle());
            taskResponse1.setDescription(tasks.getDescription());
            taskResponse1.setPriority(tasks.getPriority());
            taskResponse1.setStatus(tasks.getStatus());

            taskResponse.add(taskResponse1);

       }

       return taskResponse;

    }
}
