package com.vaibhavi.taskflow.controller;

import com.vaibhavi.taskflow.dto.TaskRequest;
import com.vaibhavi.taskflow.dto.TaskResponse;
import com.vaibhavi.taskflow.entity.Task;
import com.vaibhavi.taskflow.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> getAllTasks()
    {
        return taskService.getAllTasks();
    }

    @PostMapping("/tasks")
    public  Task createTask(@Valid @RequestBody TaskRequest request)
    {
        return taskService.createTask(request);
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable Long id)
    {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteById(@PathVariable Long id)
    {
         taskService.deleteTaskById(id);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @Valid @RequestBody Task task)
    {
        return taskService.updateTask(id, task);
    }

    @PostMapping("/tasks/{taskId}/assignTo/{userId}")
    public TaskResponse assignTask(@PathVariable Long taskId, @PathVariable Long userId)
    {
        return  taskService.assignTask(taskId, userId);
    }
}
