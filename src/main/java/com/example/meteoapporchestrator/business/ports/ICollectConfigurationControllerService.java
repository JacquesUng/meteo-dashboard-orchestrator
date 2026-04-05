package com.example.meteoapporchestrator.business.ports;

import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationResponseDto;

import java.util.List;
import java.util.UUID;

public interface ICollectConfigurationControllerService {
    List<CollectConfigurationResponseDto> getAll();

    CollectConfigurationResponseDto getOne(UUID Id);

    CollectConfigurationDto save(CollectConfigurationDto dto);
}
