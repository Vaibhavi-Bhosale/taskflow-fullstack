package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.dto.TaskRequest;
import com.vaibhavi.taskflow.dto.TaskResponse;
import com.vaibhavi.taskflow.dto.UserResponse;
import com.vaibhavi.taskflow.entity.Task;
import com.vaibhavi.taskflow.entity.User;
import com.vaibhavi.taskflow.enums.Status;
import com.vaibhavi.taskflow.exception.TaskNotFoundException;
import com.vaibhavi.taskflow.exception.UserNotFoundException;
import com.vaibhavi.taskflow.repository.TaskRepository;
import com.vaibhavi.taskflow.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

      @Autowired
      private final TaskRepository taskRepository;

      @Autowired
      private final UserRepository userRepository;

      public List<Task> getAllTasks()
      {
           return taskRepository.findAll();
      }

      public Task createTask(@Valid TaskRequest  request)
     {
          Task task = new Task();

          task.setTitle(request.getTitle());
          task.setDescription(request.getDescription() );
          task.setPriority(request.getPriority());
     
          task.setStatus(Status.PENDING);

          return  taskRepository.save(task);

     }

    public Task getTaskById(Long id)
    {
        return taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException("Task not Present"));
    }

    public void deleteTaskById(Long id)
    {
        taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException("Task not found"));

        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task task)
    {
        Task t = taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException("Task not found"));

        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setStatus(task.getStatus());
        t.setPriority(task.getPriority());

      return  taskRepository.save(t);
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
}
