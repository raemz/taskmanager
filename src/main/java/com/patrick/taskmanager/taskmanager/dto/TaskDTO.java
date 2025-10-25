package com.patrick.taskmanager.taskmanager.dto;


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

    @NotBlank
    private String status;

    @FutureOrPresent
    private LocalDateTime dueDate;

}
