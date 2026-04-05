package com.example.meteoapporchestrator.controllers.adapter;

import com.example.meteoapporchestrator.business.core.collectconfigs.ICollectConfigurationManager;
import com.example.meteoapporchestrator.business.core.tasks.INotificationTaskPlanner;
import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.business.ports.ICollectConfigurationControllerService;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectConfigurationControllerAdapter implements ICollectConfigurationControllerService {
    private final ICollectConfigurationManager manager;

    private final INotificationTaskPlanner taskPlanner;

    public CollectConfigurationControllerAdapter(
            final ICollectConfigurationManager manager,
            final INotificationTaskPlanner taskPlanner
    ) {
        this.manager = manager;
        this.taskPlanner = taskPlanner;
    }

    @Override
    public List<CollectConfigurationResponseDto> getAll() {
        List<CollectConfiguration> configList = manager.getAll();
        return configList.stream().map(config -> {
            boolean active = taskPlanner.isActive(config.Id());
            return CollectConfigurationControllerMapper.domainToResponseDto(config, active);
        }).toList();
    }

    @Override
    public CollectConfigurationResponseDto getOne(UUID Id) {
        CollectConfiguration config = manager.getOne(Id);
        boolean active = taskPlanner.isActive(Id);
        return CollectConfigurationControllerMapper.domainToResponseDto(config, active);
    }

    @Override
    public CollectConfigurationDto save(CollectConfigurationDto dto) {
        CollectConfiguration config = CollectConfigurationControllerMapper.dtoToDomain(dto);
        CollectConfiguration savedConfig = manager.save(config);
        return CollectConfigurationControllerMapper.domainToDto(savedConfig);
    }
}
