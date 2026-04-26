package com.example.meteoapporchestrator.controllers;

import com.example.meteoapporchestrator.business.ports.INotificationTaskServiceInterface;
import com.example.meteoapporchestrator.controllers.model.ScheduleTaskRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("task")
public class TaskController {
    private final INotificationTaskServiceInterface notificationTaskService;

    public TaskController(
            final INotificationTaskServiceInterface notificationTaskService
    ) {
        this.notificationTaskService = notificationTaskService;
    }

    @PostMapping
    public void scheduleTask(@RequestBody ScheduleTaskRequestDto request) {
        notificationTaskService.createTask(request.collectConfigId());
    }

    @DeleteMapping("/{configId}")
    public void deleteTask(@PathVariable String configId) {
        notificationTaskService.cancelTask(UUID.fromString(configId));
    }
}
