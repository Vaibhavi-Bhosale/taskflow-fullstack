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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

      @Autowired
      private final TaskRepository taskRepository;

      @Autowired
      private final UserRepository userRepository;public List<TaskResponse> getAllTasks() {

        List<Task> tasks = taskRepository.findAll();

        List<TaskResponse> responses = new ArrayList<>();

        for (Task task : tasks) {

            TaskResponse response = new TaskResponse();

            response.setId(task.getId());
            response.setTitle(task.getTitle());
            response.setDescription(task.getDescription());
            response.setStatus(task.getStatus());
            response.setPriority(task.getPriority());

            User assignedUser = task.getAssignedTo();

            if (assignedUser != null) {
                UserResponse userResponse = new UserResponse();

                userResponse.setId(assignedUser.getId());
                userResponse.setName(assignedUser.getName());
                userResponse.setEmail(assignedUser.getEmail());
                userResponse.setRole(assignedUser.getRole());

                response.setAssignedTo(userResponse);
            }

            responses.add(response);
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

        TaskResponse response = new TaskResponse();

        response.setId(savedTask.getId());
        response.setTitle(savedTask.getTitle());
        response.setDescription(savedTask.getDescription());
        response.setStatus(savedTask.getStatus());
        response.setPriority(savedTask.getPriority());

        return response;
    }

    public TaskResponse getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not Present"));

        TaskResponse response = new TaskResponse();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());

        User assignedUser = task.getAssignedTo();

        if (assignedUser != null) {
            UserResponse userResponse = new UserResponse();

            userResponse.setId(assignedUser.getId());
            userResponse.setName(assignedUser.getName());
            userResponse.setEmail(assignedUser.getEmail());
            userResponse.setRole(assignedUser.getRole());

            response.setAssignedTo(userResponse);
        }

        return response;
    }

    public void deleteTaskById(Long id)
    {
        taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException("Task not found"));

        taskRepository.deleteById(id);
    }

    public TaskResponse updateTask(Long id, Task task) {

        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setStatus(task.getStatus());
        t.setPriority(task.getPriority());

        Task updatedTask = taskRepository.save(t);

        TaskResponse response = new TaskResponse();

        response.setId(updatedTask.getId());
        response.setTitle(updatedTask.getTitle());
        response.setDescription(updatedTask.getDescription());
        response.setStatus(updatedTask.getStatus());
        response.setPriority(updatedTask.getPriority());

        User assignedUser = updatedTask.getAssignedTo();

        if (assignedUser != null) {
            UserResponse userResponse = new UserResponse();

            userResponse.setId(assignedUser.getId());
            userResponse.setName(assignedUser.getName());
            userResponse.setEmail(assignedUser.getEmail());
            userResponse.setRole(assignedUser.getRole());

            response.setAssignedTo(userResponse);
        }

        return response;
    }


    public TaskResponse assignTask(Long taskId, Long userId) {


         System.out.println("\n\n\n\n\n taskId : " + taskId + "userId : " + userId + "\n\n\n\n");
        Optional<User> optionalUser= userRepository.findById(userId);

        if(optionalUser.isEmpty())
        {
            throw  new UserNotFoundException("User Not Found...");
        }

        User user = optionalUser.get();


        Optional <Task> optionalTask = taskRepository.findById(taskId);

        if(optionalTask.isEmpty())
        {
          throw  new TaskNotFoundException("Task Not Found");
        }

        Task task = optionalTask.get();

        task.setAssignedTo(user);


       Task updatedTask = taskRepository.save(task);

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(updatedTask.getId());
        taskResponse.setTitle(updatedTask.getTitle());
        taskResponse.setDescription(updatedTask.getDescription());
        taskResponse.setStatus(updatedTask.getStatus());
        taskResponse.setPriority(updatedTask.getPriority());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());


        taskResponse.setAssignedTo(userResponse);

        return taskResponse;

    }

    public TaskResponse updateTaskStatus(long taskId, UpdateTaskStatusRequest updateTaskStatusRequest) {

          Optional<Task> t =  taskRepository.findById(taskId);

          if(t.isEmpty())
          {
              throw new TaskNotFoundException("Task not found");
          }

          Task task = t.get();



        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (currentUser.getRole() != UserRole.ADMIN) {

            if (task.getAssignedTo() == null) {
                throw new RuntimeException("Task is not assigned to any user");
            }

            if (!Objects.equals(task.getAssignedTo().getId(), currentUser.getId())) {
                throw new RuntimeException("You cannot update another user's task");
            }
        }

          task.setStatus(updateTaskStatusRequest.getStatus());

         Task fullTask = taskRepository.save(task);

         TaskResponse taskResponse = new TaskResponse();

         taskResponse.setId(fullTask.getId());
         taskResponse.setTitle(fullTask.getTitle());
         taskResponse.setDescription(fullTask.getDescription());
         taskResponse.setStatus(fullTask.getStatus());
         taskResponse.setPriority(fullTask.getPriority());


//         taskResponse.setAssignedTo(fullTask.getAssignedTo());


        User assignedUser = fullTask.getAssignedTo();

        if (assignedUser != null) {
            UserResponse userResponse = new UserResponse();

            userResponse.setId(assignedUser.getId());
            userResponse.setName(assignedUser.getName());
            userResponse.setEmail(assignedUser.getEmail());
            userResponse.setRole(assignedUser.getRole());

            taskResponse.setAssignedTo(userResponse);
        }

         return taskResponse;

    }

}
