package com.example.meteoapporchestrator.persistence.jpa;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.persistence.jpa.model.CollectConfigurationEntity;

public class CollectConfigurationPersistenceMapper {
    public static CollectConfiguration entityToDomain(CollectConfigurationEntity entity) {
        return new CollectConfiguration(entity.getId(), entity.getName(), entity.getStartDate(), entity.getTimespan());
    }

    public static CollectConfigurationEntity domainToEntity(CollectConfiguration config) {
        CollectConfigurationEntity entity = new CollectConfigurationEntity(
                config.name(),
                config.startDate(),
                config.timespan()
        );
        entity.setId(config.Id());
        return entity;
    }
}
