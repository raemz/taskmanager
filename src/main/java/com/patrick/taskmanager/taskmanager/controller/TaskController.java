package com.patrick.taskmanager.taskmanager.controller;

import com.patrick.taskmanager.taskmanager.dto.TaskDTO;
import com.patrick.taskmanager.taskmanager.entity.Task;
import com.patrick.taskmanager.taskmanager.exception.TaskNotFoundException;
import com.patrick.taskmanager.taskmanager.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepo;

    public TaskController(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    // Get all task
    @GetMapping
    public List<Task> getAllTask() {
        return taskRepo.findAll();
    }

    // Get a specific Task
    @GetMapping("{id}")
    public Task getTask(@PathVariable Long id) {
        return taskRepo.findById(id).orElseThrow(() ->
                    new TaskNotFoundException("Task with ID " + id + " is not found!")
                );
    }

    // Add a task
    @PostMapping
    public Task addTask(@Valid @RequestBody TaskDTO taskDTO) {

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());
        return taskRepo.save(task);
    }

    // Update a Task
    // ToDO If you dont want to update a certain field, that field must be the same as before it was updated.
    @PutMapping("/{id}")
    public Task updateTask(@Valid @RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        Task task = taskRepo.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with ID " + id + " is not found!")
        );

        if(!taskDTO.getTitle().isBlank()) {
            task.setTitle(taskDTO.getTitle());
        }

        if(!taskDTO.getDescription().isBlank()) {
            task.setDescription(taskDTO.getDescription());
        }

        if(!taskDTO.getStatus().isBlank()) {
            task.setStatus(taskDTO.getStatus());
        }

        if(!taskDTO.getDueDate().isBefore(LocalDateTime.now())) {
            task.setDueDate(taskDTO.getDueDate());;
        }

        return taskRepo.save(task);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        Task task = taskRepo.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with ID " + id + " is not found!")
        );
        taskRepo.delete(task);
    }

}
