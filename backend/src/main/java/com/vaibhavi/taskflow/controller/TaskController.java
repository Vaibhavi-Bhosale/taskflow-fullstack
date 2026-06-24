package com.vaibhavi.taskflow.controller;

import com.vaibhavi.taskflow.entity.Task;
import com.vaibhavi.taskflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/allTasks")
    public List<Task> getAllTasks()
    {
        return taskService.getAllTasks();
    }

    @PostMapping("/createTask")
    public  Task createTask(@RequestBody Task task)
    {
        return taskService.createTask(task);
    }
}
