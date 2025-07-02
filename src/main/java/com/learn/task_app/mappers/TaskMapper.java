package com.learn.task_app.mappers;

import com.learn.task_app.domain.dto.TaskDto;
import com.learn.task_app.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
