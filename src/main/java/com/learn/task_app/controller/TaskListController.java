package com.learn.task_app.controller;


import com.learn.task_app.domain.dto.TaskListDto;
import com.learn.task_app.mappers.TaskListMapper;
import com.learn.task_app.service.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/task-lists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        return taskListMapper.toDto(
                taskListService.createTaskList(taskListMapper.fromDto(taskListDto))
        );
    }

    @GetMapping("/{task_list_id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID taskListId) {
        return taskListService.getTaskListById(taskListId)
                .map(taskListMapper::toDto);
    }
    @PutMapping("/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID taskListId,
                                      @RequestBody TaskListDto taskListDto) {
        return taskListMapper.toDto(
                taskListService.updateTaskList(taskListId, taskListMapper.fromDto(taskListDto))
        );
    }
    @DeleteMapping("/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID taskListId) {
        taskListService.deleteTaskList(taskListId);
    }
}
