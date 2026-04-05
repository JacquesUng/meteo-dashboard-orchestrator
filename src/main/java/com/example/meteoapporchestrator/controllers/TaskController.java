package com.example.meteoapporchestrator.controllers;

import com.example.meteoapporchestrator.business.ports.INotificationTaskServiceInterface;
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
    public void scheduleTask(@RequestBody String collectConfigId) {
        notificationTaskService.createTask(UUID.fromString(collectConfigId));
    }

    @DeleteMapping("/{configId}")
    public void deleteTask(@PathVariable String configId) {
        notificationTaskService.cancelTask(UUID.fromString(configId));
    }
}
