package com.example.meteoapporchestrator.business.core.collectconfigs;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;

import java.util.List;
import java.util.UUID;

public interface ICollectConfigurationManager {
    List<CollectConfiguration> getAll();

    CollectConfiguration getOne(UUID Id);

    CollectConfiguration save(CollectConfiguration config);
}
