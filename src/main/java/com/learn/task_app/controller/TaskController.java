package com.learn.task_app.controller;


import com.learn.task_app.domain.dto.TaskDto;
import com.learn.task_app.mappers.TaskMapper;
import com.learn.task_app.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-lists/{task_list_id}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID task_list_id) {
        return taskService.listTasks(task_list_id).stream().map(taskMapper::toDto).toList();
    }
    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID task_list_id, @RequestBody TaskDto taskDto) {
        return taskMapper.toDto(taskService.createTask(task_list_id, taskMapper.fromDto(taskDto)));
    }
    @GetMapping("/{task_id}")
    public TaskDto getTask(@PathVariable("task_list_id") UUID task_list_id, @PathVariable("task_id") UUID task_id) {
        return taskService.getTask(task_list_id, task_id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + task_id));
    }
    @PutMapping("/{task_id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID task_list_id, @PathVariable("task_id") UUID task_id, @RequestBody TaskDto taskDto) {
        return taskMapper.toDto(taskService.updateTask(task_list_id, task_id, taskMapper.fromDto(taskDto)));
    }
    @DeleteMapping("/{task_id}")
    public void deleteTask(@PathVariable("task_list_id") UUID task_list_id, @PathVariable("task_id") UUID task_id) {
        taskService.deleteTask(task_list_id, task_id);
    }
}
