package com.patrick.taskmanager.taskmanager.dto;


import com.patrick.taskmanager.taskmanager.model.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TaskDTO {

    @NotBlank
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @FutureOrPresent
    private LocalDateTime dueDate;

}
