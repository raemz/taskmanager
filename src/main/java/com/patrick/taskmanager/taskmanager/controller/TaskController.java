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

    @GetMapping
    public List<Task> getAllTask() {
        return taskRepo.findAll();
    }

    @PostMapping
    public Task addTask(@RequestBody TaskDTO taskDTO) {

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setDueDate(taskDTO.getDueDate());
        return taskRepo.save(task);
    }

}
