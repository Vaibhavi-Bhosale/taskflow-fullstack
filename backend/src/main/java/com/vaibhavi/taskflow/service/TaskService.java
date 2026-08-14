package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.dto.TaskRequest;
import com.vaibhavi.taskflow.dto.TaskResponse;
import com.vaibhavi.taskflow.dto.UpdateTaskStatusRequest;
import com.vaibhavi.taskflow.dto.UserResponse;
import com.vaibhavi.taskflow.entity.Task;
import com.vaibhavi.taskflow.entity.User;
import com.vaibhavi.taskflow.enums.Status;
import com.vaibhavi.taskflow.enums.UserRole;
import com.vaibhavi.taskflow.exception.TaskNotFoundException;
import com.vaibhavi.taskflow.exception.UserNotFoundException;
import com.vaibhavi.taskflow.repository.TaskRepository;
import com.vaibhavi.taskflow.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public List<TaskResponse> getAllTasks() {

        List<Task> tasks = taskRepository.findAll();

        List<TaskResponse> responses = new ArrayList<>();

        for (Task task : tasks) {
            responses.add(mapTaskToResponse(task));
        }

        return responses;
    }


    public TaskResponse createTask(@Valid TaskRequest request) {

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(Status.PENDING);

        Task savedTask = taskRepository.save(task);

        return mapTaskToResponse(savedTask);
    }


    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        return mapTaskToResponse(task);
    }


    public void deleteTaskById(Long id) {

        taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        taskRepository.deleteById(id);
    }


    public TaskResponse updateTask(
            Long id,
             TaskRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());

        Task updatedTask = taskRepository.save(task);

        return mapTaskToResponse(updatedTask);
    }


    public TaskResponse assignTask(Long taskId, Long userId) {

        Optional<User> optionalUser =
                userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = optionalUser.get();

        Optional<Task> optionalTask =
                taskRepository.findById(taskId);

        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }

        Task task = optionalTask.get();

        task.setAssignedTo(user);

        Task updatedTask = taskRepository.save(task);

        return mapTaskToResponse(updatedTask);
    }


    public TaskResponse updateTaskStatus(
            long taskId,
            UpdateTaskStatusRequest request) {

        Optional<Task> optionalTask =
                taskRepository.findById(taskId);

        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }

        Task task = optionalTask.get();

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (currentUser.getRole() != UserRole.ADMIN) {

            if (task.getAssignedTo() == null) {
                throw new RuntimeException(
                        "Task is not assigned to any user");
            }

            if (!Objects.equals(
                    task.getAssignedTo().getId(),
                    currentUser.getId())) {

                throw new RuntimeException(
                        "You cannot update another user's task");
            }
        }

        task.setStatus(request.getStatus());

        Task updatedTask = taskRepository.save(task);

        return mapTaskToResponse(updatedTask);
    }


    private TaskResponse mapTaskToResponse(Task task) {

        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());

        if (task.getAssignedTo() != null) {
            response.setAssignedTo(
                    mapUserToResponse(task.getAssignedTo())
            );
        }

        return response;
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