package com.example.meteoapporchestrator.controllers;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;
import com.example.meteoapporchestrator.business.ICollectConfigurationService;
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

    public CollectConfigurationController(final ICollectConfigurationService collectionConfigurationService) {
        this.collectionConfigurationService = collectionConfigurationService;
    }

    @GetMapping("/all")
    public List<CollectConfigurationDTO> getAll() {
        return collectionConfigurationService.getAll();
    }

    @GetMapping("/{Id}")
    public CollectConfigurationDTO getOne(@PathVariable UUID Id) {
        return collectionConfigurationService.getOne(Id);
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
