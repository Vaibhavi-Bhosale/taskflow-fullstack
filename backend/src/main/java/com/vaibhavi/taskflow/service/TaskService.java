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

    public Task getTaskById(Long id)
    {
        return taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not Present"));
    }

    public void deleteTaskById(Long id)
    {
        taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found"));

        taskRepository.deleteById(id);
    }

    public Task updateTask(Long id, Task task)
    {
        Task t = taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Task not found"));

        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setStatus(task.getStatus());
        t.setPriority(task.getPriority());

      return  taskRepository.save(t);
    }

    
}
