package com.learn.task_app.domain.dto;

import com.learn.task_app.domain.entities.TaskPriority;
import com.learn.task_app.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(

    UUID id,
    String title,
    String description,
    LocalDateTime dueDate,
    TaskPriority priority,
    TaskStatus status

    ){

}
