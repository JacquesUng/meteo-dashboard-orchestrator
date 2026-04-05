package com.example.meteoapporchestrator.controllers.adapter;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationResponseDto;

public class CollectConfigurationControllerMapper {
    public static CollectConfiguration dtoToDomain(CollectConfigurationDto dto) {
        return new CollectConfiguration(dto.Id(), dto.name(), dto.startDate(), dto.timespan());
    }

    public static CollectConfigurationDto domainToDto(CollectConfiguration config) {
        return new CollectConfigurationDto(config.Id(), config.name(), config.startDate(), config.timespan());
    }

    public static CollectConfigurationResponseDto domainToResponseDto(CollectConfiguration config, boolean active) {
        return new CollectConfigurationResponseDto(config.Id(), config.name(), config.startDate(), config.timespan(), active);
    }
}
