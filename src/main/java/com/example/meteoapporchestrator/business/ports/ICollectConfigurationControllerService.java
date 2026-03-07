package com.example.meteoapporchestrator.business.ports;

import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;

import java.util.List;
import java.util.UUID;

public interface ICollectConfigurationControllerService {
    List<CollectConfigurationDto> getAll();

    CollectConfigurationDto getOne(UUID Id);

    CollectConfigurationDto save(CollectConfigurationDto dto);
}
