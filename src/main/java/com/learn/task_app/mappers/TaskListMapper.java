package com.learn.task_app.mappers;

import com.learn.task_app.domain.dto.TaskListDto;
import com.learn.task_app.domain.entities.Task;
import com.learn.task_app.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
