package com.example.meteoapporchestrator.controllers.adapter;

import com.example.meteoapporchestrator.business.core.collectconfigs.ICollectConfigurationManager;
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

    private final NotificationTaskAdapter notificationTaskAdapter;

    public CollectConfigurationControllerAdapter(
            final ICollectConfigurationManager manager,
            final NotificationTaskAdapter notificationTaskAdapter
    ) {
        this.manager = manager;
        this.notificationTaskAdapter = notificationTaskAdapter;
    }

    @Override
    public List<CollectConfigurationResponseDto> getAll() {
        List<CollectConfiguration> configList = manager.getAll();
        return configList.stream().map(config -> {
            boolean active = notificationTaskAdapter.isActive(config.Id());
            return CollectConfigurationControllerMapper.domainToResponseDto(config, active);
        }).toList();
    }

    @Override
    public CollectConfigurationResponseDto getOne(UUID Id) {
        CollectConfiguration config = manager.getOne(Id);
        boolean active = notificationTaskAdapter.isActive(Id);
        return CollectConfigurationControllerMapper.domainToResponseDto(config, active);
    }

    @Override
    public CollectConfigurationDto save(CollectConfigurationDto dto) {
        CollectConfiguration config = CollectConfigurationControllerMapper.dtoToDomain(dto);
        CollectConfiguration savedConfig = manager.save(config);
        return CollectConfigurationControllerMapper.domainToDto(savedConfig);
    }

    @Override
    public void deleteOne(UUID Id) {
        manager.deleteOne(Id);
    }
}
