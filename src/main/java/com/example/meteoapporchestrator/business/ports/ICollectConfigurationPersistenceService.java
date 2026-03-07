package com.example.meteoapporchestrator.business.ports;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;

import java.util.List;
import java.util.UUID;

public interface ICollectConfigurationPersistenceService {
    List<CollectConfiguration> getAll();

    CollectConfiguration getOne(UUID Id);

    CollectConfiguration save(CollectConfiguration config);
}
