package com.example.meteoapporchestrator.controllers.adapter;

import com.example.meteoapporchestrator.business.core.collectconfigs.ICollectConfigurationManager;
import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.business.ports.ICollectConfigurationControllerService;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectConfigurationControllerAdapter implements ICollectConfigurationControllerService {
    private final ICollectConfigurationManager manager;

    public CollectConfigurationControllerAdapter(final ICollectConfigurationManager manager) {
        this.manager = manager;
    }

    @Override
    public List<CollectConfigurationDto> getAll() {
        List<CollectConfiguration> configList = manager.getAll();
        return configList.stream().map(CollectConfigurationControllerMapper::domainToDto).toList();
    }

    @Override
    public CollectConfigurationDto getOne(UUID Id) {
        CollectConfiguration config = manager.getOne(Id);
        return CollectConfigurationControllerMapper.domainToDto(config);
    }

    @Override
    public CollectConfigurationDto save(CollectConfigurationDto dto) {
        CollectConfiguration config = CollectConfigurationControllerMapper.dtoToDomain(dto);
        CollectConfiguration savedConfig = manager.save(config);
        return CollectConfigurationControllerMapper.domainToDto(savedConfig);
    }
}
