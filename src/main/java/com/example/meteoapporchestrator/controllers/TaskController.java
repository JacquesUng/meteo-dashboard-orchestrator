package com.example.meteoapporchestrator.controllers;

import com.example.meteoapporchestrator.business.ports.ICollectConfigurationControllerService;
import com.example.meteoapporchestrator.business.core.tasks.INotificationTaskPlanner;
import com.example.meteoapporchestrator.business.ports.INotificationTaskServiceInterface;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("task")
public class TaskController {
    private final ICollectConfigurationControllerService collectConfigurationService;

    private final INotificationTaskServiceInterface notificationTaskService;

    public TaskController(
            final ICollectConfigurationControllerService collectConfigurationService,
            final INotificationTaskServiceInterface notificationTaskService
    ) {
        this.collectConfigurationService = collectConfigurationService;
        this.notificationTaskService = notificationTaskService;
    }

    @PostMapping
    public void scheduleTask(@RequestBody String collectConfigId) {
        CollectConfigurationDto configDto = collectConfigurationService.getOne(UUID.fromString(collectConfigId));
        notificationTaskService.createTask(configDto);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable String id) {
        notificationTaskService.cancelTask(UUID.fromString(id));
    }
}
