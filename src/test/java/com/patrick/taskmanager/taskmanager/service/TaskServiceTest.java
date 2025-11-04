package com.patrick.taskmanager.taskmanager.service;

import com.patrick.taskmanager.taskmanager.entity.Task;
import com.patrick.taskmanager.taskmanager.exception.TaskNotFoundException;
import com.patrick.taskmanager.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock // Mock of the repository
    private TaskRepository taskRepo;

    @InjectMocks // Inject the mock repository to be used in the TaskService, not the real repo.
    private TaskService taskService;

    // Get Task Testing:
    @Test
    void getTaskById_shouldReturnTask_whenFound() {
        Task mockTask = new Task();
        mockTask.setId(1L);
        mockTask.setTitle("Read Book");

        when(taskRepo.findById(1L)).thenReturn(Optional.of(mockTask));

        Task result = taskService.getTask(1L);

        assertEquals("Read Book", result.getTitle());
    }

    @Test
    void getTaskById_shouldThrow_whenNotFound() {
        when(taskRepo.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(TaskNotFoundException.class,
                () -> taskService.getTask(99L)
        );

        assertEquals("Task with ID " + 99L + " is not found!", ex.getMessage());

    }

}
