package com.learn.task_app.service.impl;

import com.learn.task_app.domain.entities.TaskList;
import com.learn.task_app.repositories.TaskListRepo;
import com.learn.task_app.service.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepo taskListRepo;

    public TaskListServiceImpl(TaskListRepo taskListRepo) {
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepo.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(taskList.getTitle()==null || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list title cannot be null or blank");
        }
        LocalDateTime now = LocalDateTime.now();
        return taskListRepo.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                now,
                null,
                now
        ));

    }

    @Override
    public Optional<TaskList> getTaskListById(UUID id) {
        return taskListRepo.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if(!Objects.equals(taskList.getId(),id)){
            throw new IllegalArgumentException("Task list ID in the request does not match the ID in the URL");
        }
        TaskList existingTaskList = taskListRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task list not found with ID: " + id));
        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdatedAt(LocalDateTime.now());
        return taskListRepo.save(existingTaskList);

    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepo.deleteById(id);
    }


}
