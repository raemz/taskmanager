package com.patrick.taskmanager.taskmanager.controller;

import com.patrick.taskmanager.taskmanager.dto.TaskDTO;
import com.patrick.taskmanager.taskmanager.entity.Task;
import com.patrick.taskmanager.taskmanager.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

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

    // Add a task
    @PostMapping
    public Task addTask(@RequestBody TaskDTO taskDTO) {

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());
        return taskRepo.save(task);
    }

    // Update a Task
    // ToDO If you dont wannt to update a certain field, that field must be the same as before it was updated.
    @PutMapping("/{id}")
    public Task updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        Task task = taskRepo.findById(id).orElseThrow();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());

        return taskRepo.save(task);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        Task task = taskRepo.findById(id).orElseThrow();
        taskRepo.delete(task);
    }

}
