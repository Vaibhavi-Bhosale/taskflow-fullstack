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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;


    public UserResponse createUser(UserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(
                    "Email already exists. Try another one."
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);

        return mapUserToResponse(savedUser);
    }


    public List<TaskResponse> getUserTasks(Long userId) {

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (currentUser.getRole() != UserRole.ADMIN
                && !Objects.equals(userId, currentUser.getId())) {

            throw new RuntimeException(
                    "You cannot access another user's tasks"
            );
        }

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        List<Task> taskList =
                taskRepository.findByAssignedToId(userId);

        if (taskList.isEmpty()) {
            throw new TaskNotFoundException(
                    "No task found for the user"
            );
        }

        List<TaskResponse> responses = new ArrayList<>();

        for (Task task : taskList) {

            TaskResponse response = new TaskResponse();

            response.setId(task.getId());
            response.setTitle(task.getTitle());
            response.setDescription(task.getDescription());
            response.setPriority(task.getPriority());
            response.setStatus(task.getStatus());

            if (task.getAssignedTo() != null) {
                response.setAssignedTo(
                        mapUserToResponse(task.getAssignedTo())
                );
            }

            responses.add(response);
        }

        return responses;
    }


    private UserResponse mapUserToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;
    }
}