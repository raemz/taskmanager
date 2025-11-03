package com.patrick.taskmanager.taskmanager.controller;

import com.patrick.taskmanager.taskmanager.dto.TaskDTO;
import com.patrick.taskmanager.taskmanager.entity.Task;
import com.patrick.taskmanager.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Get all task
    @GetMapping
    public List<Task> getAllTask() {
        return taskService.getAllTask();
    }

    // Get a specific Task
    @GetMapping("{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    // Add a task
    @PostMapping
    public Task addTask(@Valid @RequestBody TaskDTO taskDTO) {
        return taskService.addTask(taskDTO);
    }

    // Update a Task
    @PutMapping("/{id}")
    public Task updateTask(@Valid @RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        return taskService.updateTask(taskDTO, id);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
