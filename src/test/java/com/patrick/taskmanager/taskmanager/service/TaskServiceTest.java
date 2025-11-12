package com.patrick.taskmanager.taskmanager.service;

import com.patrick.taskmanager.taskmanager.dto.TaskDTO;
import com.patrick.taskmanager.taskmanager.entity.Task;
import com.patrick.taskmanager.taskmanager.exception.TaskNotFoundException;
import com.patrick.taskmanager.taskmanager.model.TaskStatus;
import com.patrick.taskmanager.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepo;

    @InjectMocks
    private TaskService taskService;

    private Task existingTask;
    private TaskDTO dto;

    @BeforeEach
    void setup() {
        existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Study");
        existingTask.setDescription("Read Chapter 5");
        existingTask.setStatus(TaskStatus.PENDING);
        existingTask.setDueDate(LocalDateTime.now().plusDays(1));

        dto = new TaskDTO();
        dto.setTitle("Updated Task");
        dto.setDescription("Updated Description");
        dto.setStatus(TaskStatus.COMPLETED);
        dto.setDueDate(LocalDateTime.now().plusDays(2));
    }

    // Get all task
    @Test
    void getAllTask_shouldReturnListOfAllTasks() {
        when(taskRepo.findAll()).thenReturn(List.of(existingTask));

        List<Task> tasks = taskService.getAllTask();

        assertEquals(1, tasks.size());
        assertEquals("Study", tasks.getFirst().getTitle());
        verify(taskRepo).findAll();
    }

    // Get specific task - found
    @Test
    void getTask_shouldReturnTask_whenFound() {
        when(taskRepo.findById(1L)).thenReturn(Optional.of(existingTask));

        Task result = taskService.getTask(1L);

        assertEquals("Study", result.getTitle());
        verify(taskRepo).findById(1L);
    }

    // Get specified task - not found
    @Test
    void getTask_shouldThrowException_whenNotFound() {
        when(taskRepo.findById(99L)).thenReturn(Optional.empty());

        TaskNotFoundException ex = assertThrows(TaskNotFoundException.class,
                () -> taskService.getTask(99L)
                );

        assertEquals("Task with ID 99 is not found!", ex.getMessage());
    }

    // Add task
    @Test
    void addTask_shouldSaveAndReturnTask() {
        when(taskRepo.save(any(Task.class))).thenAnswer(invocation -> {
            Task t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        Task result = taskService.addTask(dto);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals(1L, result.getId());
        verify(taskRepo).save(any(Task.class));

    }

    // Update task - found
    @Test
    void updateTask_shouldUpdateFields_whenFound() {
        when(taskRepo.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepo.save(any(Task.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        Task updated = taskService.updateTask(dto, 1L);
        assertEquals("Updated Task", updated.getTitle());
        assertEquals("Updated Description", updated.getDescription());
        assertEquals(TaskStatus.COMPLETED, updated.getStatus());
        verify(taskRepo).findById(1L);
        verify(taskRepo).save(existingTask);
    }

    // Update task - conditional logic (ignore blank)
    @Test
    void updateTask_shouldIgnoreBlankFields() {
        when(taskRepo.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepo.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDTO partial = new TaskDTO();
        partial.setTitle("");
        partial.setDescription("");
        partial.setStatus(TaskStatus.COMPLETED);
        partial.setDueDate(LocalDateTime.now().plusDays(5));

        Task updated = taskService.updateTask(partial, 1L);

        assertEquals("Study", updated.getTitle());
        assertEquals("Read Chapter 5", updated.getDescription());
        assertEquals(TaskStatus.COMPLETED, updated.getStatus());
        verify(taskRepo).findById(1L);
        verify(taskRepo).save(existingTask);

    }

    // Update task - not found
    @Test
    void updateTask_shouldThrowException_whenNotFound() {
        when(taskRepo.findById(999L)).thenReturn(Optional.empty());

        TaskNotFoundException ex = assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTask(dto, 999L));

        assertEquals("Task with ID 999 is not found!", ex.getMessage());
        verify(taskRepo, never()).save(any());
    }

    // Delete Task - found
    @Test
    void deleteTask_shouldDeleteSpecifiedTask_whenFound() {
        when(taskRepo.findById(1L)).thenReturn(Optional.of(existingTask));

        taskService.deleteTask(1L);

        verify(taskRepo).delete(existingTask);

    }

    // Delete Task - not found
    @Test
    void deleteTask_shouldThrowException_whenNotFound() {
        when(taskRepo.findById(999L)).thenReturn(Optional.empty());

        TaskNotFoundException ex = assertThrows(TaskNotFoundException.class,
                () -> taskService.deleteTask(999L));

        assertEquals("Task with ID 999 is not found!", ex.getMessage());
    }


}
