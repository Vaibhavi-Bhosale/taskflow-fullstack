package com.vaibhavi.taskflow.controller;

import com.vaibhavi.taskflow.entity.Task;
import com.vaibhavi.taskflow.service.TaskService;
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

    @PostMapping("/createTasks")
    public  Task createTask(@RequestBody Task task)
    {
        return taskService.createTask(task);
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
    public Task updateTask(@PathVariable Long id, @RequestBody Task task)
    {
        return taskService.updateTask(id, task);
    }
}
