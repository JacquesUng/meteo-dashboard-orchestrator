package com.example.meteoapporchestrator.business.core.collectconfigs;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.business.ports.ICollectConfigurationPersistenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectConfigurationManager implements ICollectConfigurationManager {
    private final ICollectConfigurationPersistenceService persistenceService;

    public CollectConfigurationManager(final ICollectConfigurationPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public List<CollectConfiguration> getAll() {
        return persistenceService.getAll();
    }

    @Override
    public CollectConfiguration getOne(UUID Id) {
        return persistenceService.getOne(Id);
    }

    @Override
    public CollectConfiguration save(CollectConfiguration config) {
        return persistenceService.save(config);
    }

    @Override
    public void deleteOne(UUID Id) {
        persistenceService.delete(Id);
    }
}
