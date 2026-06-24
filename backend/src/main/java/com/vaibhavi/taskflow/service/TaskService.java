package com.vaibhavi.taskflow.service;

import com.vaibhavi.taskflow.entity.Task;
import com.vaibhavi.taskflow.enums.Status;
import com.vaibhavi.taskflow.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

      private final TaskRepository taskRepository;

      public List<Task> getAllTasks()
      {
           return taskRepository.findAll();
      }

      public Task createTask(Task task)
    {
         task.setStatus(Status.PENDING);

          return  taskRepository.save(task);

    }
}
