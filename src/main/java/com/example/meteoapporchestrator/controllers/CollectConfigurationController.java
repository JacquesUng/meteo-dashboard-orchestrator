package com.example.meteoapporchestrator.controllers;

import com.example.meteoapporchestrator.business.ports.ICollectConfigurationControllerService;
import com.example.meteoapporchestrator.business.core.tasks.INotificationTaskPlanner;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import com.example.meteoapporchestrator.controllers.validators.NotNullId;
import com.example.meteoapporchestrator.controllers.validators.NullId;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/collect-configuration")
public class CollectConfigurationController {
    private final ICollectConfigurationControllerService service;

    private final INotificationTaskPlanner taskPlanner;

    public CollectConfigurationController(
            final ICollectConfigurationControllerService service,
            final INotificationTaskPlanner taskPlanner
    ) {
        this.service = service;
        this.taskPlanner = taskPlanner;
    }

    @GetMapping("/all")
    public List<CollectConfigurationDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/active")
    public List<UUID> getAllActiveIds() {
        return taskPlanner.getAllActiveConfigIds();
    }

    @GetMapping("/{id}")
    public CollectConfigurationDto getOne(@PathVariable UUID id) {
        return service.getOne(id);
    }

    @PostMapping
    public CollectConfigurationDto createOne(@RequestBody @Valid @NullId CollectConfigurationDto dto) {
        return service.save(dto);
    }

    @PutMapping
    public CollectConfigurationDto updateOne(@RequestBody @Valid @NotNullId CollectConfigurationDto dto) {
        return service.save(dto);
    }
}
