package com.example.meteoapporchestrator.controllers;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;
import com.example.meteoapporchestrator.business.services.ICollectConfigurationService;
import com.example.meteoapporchestrator.business.tasks.INotificationTaskPlanner;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("task")
public class TaskController {
    private final ICollectConfigurationService collectionConfigurationService;

    private final INotificationTaskPlanner taskPlanner;

    public TaskController(
            final ICollectConfigurationService collectionConfigurationService,
            final INotificationTaskPlanner taskPlanner
    ) {
        this.collectionConfigurationService = collectionConfigurationService;
        this.taskPlanner = taskPlanner;
    }

    @PostMapping
    public void scheduleTask(@RequestBody String collectConfigId) {
        CollectConfigurationDTO configDto = collectionConfigurationService.getOne(UUID.fromString(collectConfigId));
        taskPlanner.createTask(configDto);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable String id) {
        taskPlanner.cancelTask(UUID.fromString(id));
    }
}
