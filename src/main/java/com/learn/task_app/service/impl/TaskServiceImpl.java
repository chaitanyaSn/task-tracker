package com.learn.task_app.service.impl;

import com.learn.task_app.domain.entities.Task;
import com.learn.task_app.domain.entities.TaskList;
import com.learn.task_app.domain.entities.TaskPriority;
import com.learn.task_app.domain.entities.TaskStatus;
import com.learn.task_app.repositories.TaskListRepo;
import com.learn.task_app.repositories.TaskRepo;
import com.learn.task_app.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final TaskListRepo taskListRepo;
    public TaskServiceImpl(TaskRepo taskRepo, TaskListRepo taskListRepo) {
        this.taskRepo = taskRepo;
        this.taskListRepo = taskListRepo;
    }
    @Override
    public List<Task> listTasks(UUID id) {
        return taskRepo.findByTaskListId(id);
    }

   @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(task.getTitle()==null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task title cannot be null or blank");
        }
        TaskPriority taskPriority= Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus=TaskStatus.OPEN;
        TaskList taskList=taskListRepo.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Task list not found with ID: " + taskListId));
        LocalDateTime now = LocalDateTime.now();
        Task newTask = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                taskStatus,
                taskPriority,
                task.getDueDate(),
                taskList,
                now,
                now

        );
        return taskRepo.save(newTask);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepo.findByTaskListIdAndId(taskListId,taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {

        if(!Objects.equals(task.getId(), taskId)){
            throw new IllegalArgumentException("Task ID in the request does not match the ID in the URL");
        }
        Task existingTask = taskRepo.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdatedAt(LocalDateTime.now());
        return taskRepo.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepo.deleteByTaskListIdAndId(taskListId,taskId);
    }


}
