package com.patrick.taskmanager.taskmanager.dto;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class TaskDTO {
    @NotBlank
    private String title;

    private String description;

    @NotBlank(message = "Status is required")
    private String status;

    @FutureOrPresent(message = "Due date must be today or in the future!")
    private LocalDateTime dueDate;

}
