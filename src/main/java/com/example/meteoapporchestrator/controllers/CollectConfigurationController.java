package com.example.meteoapporchestrator.controllers;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;
import com.example.meteoapporchestrator.business.services.ICollectConfigurationService;
import com.example.meteoapporchestrator.business.tasks.NotificationTaskPlannerImpl;
import com.example.meteoapporchestrator.controllers.validators.NotNullId;
import com.example.meteoapporchestrator.controllers.validators.NullId;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/collect-configuration")
public class CollectConfigurationController {
    private final ICollectConfigurationService collectionConfigurationService;

    private final NotificationTaskPlannerImpl taskPlanner;

    public CollectConfigurationController(
            final ICollectConfigurationService collectionConfigurationService,
            final NotificationTaskPlannerImpl taskPlanner
    ) {
        this.collectionConfigurationService = collectionConfigurationService;
        this.taskPlanner = taskPlanner;
    }

    @GetMapping("/all")
    public List<CollectConfigurationDTO> getAll() {
        return collectionConfigurationService.getAll();
    }

    @GetMapping("/active")
    public List<UUID> getAllActiveIds() {
        return taskPlanner.getAllActiveConfigIds();
    }

    @GetMapping("/{id}")
    public CollectConfigurationDTO getOne(@PathVariable UUID id) {
        return collectionConfigurationService.getOne(id);
    }

    @PostMapping
    public CollectConfigurationDTO createOne(@RequestBody @Valid @NullId CollectConfigurationDTO dto) {
        return collectionConfigurationService.save(dto);
    }

    @PutMapping
    public CollectConfigurationDTO updateOne(@RequestBody @Valid @NotNullId CollectConfigurationDTO dto) {
        return collectionConfigurationService.save(dto);
    }
}
