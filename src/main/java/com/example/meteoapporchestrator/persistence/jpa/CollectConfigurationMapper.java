package com.example.meteoapporchestrator.persistence.jpa;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;
import com.example.meteoapporchestrator.persistence.jpa.model.CollectConfiguration;

public class CollectConfigurationMapper {
    public static CollectConfigurationDTO entityToDto(CollectConfiguration entity) {
        return new CollectConfigurationDTO(entity.getId(), entity.getName(), entity.getStartDate(), entity.getTimespan());
    }

    public static CollectConfiguration dtoToEntity(CollectConfigurationDTO dto) {
        CollectConfiguration entity = new CollectConfiguration(dto.name(), dto.startDate(), dto.timespan());
        entity.setId(dto.Id());
        return entity;
    }
}
