package com.example.meteoapporchestrator.persistence.jpa;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.business.ports.ICollectConfigurationPersistenceService;
import com.example.meteoapporchestrator.persistence.jpa.model.CollectConfigurationEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CollectConfigurationJpaAdapter implements ICollectConfigurationPersistenceService {
    private final ICollectConfigurationRepository repository;

    public CollectConfigurationJpaAdapter(final ICollectConfigurationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CollectConfiguration> getAll() {
        List<CollectConfigurationEntity> collectConfigurationEntityList = repository.findAll();
        return collectConfigurationEntityList.stream().map(CollectConfigurationPersistenceMapper::entityToDomain).toList();
    }

    @Override
    public CollectConfiguration getOne(UUID Id) {
        Optional<CollectConfigurationEntity> entity = repository.findById(Id);
        if (entity.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return CollectConfigurationPersistenceMapper.entityToDomain(entity.get());
    }

    @Override
    public CollectConfiguration save(CollectConfiguration config) {
        CollectConfigurationEntity entity = repository.save(CollectConfigurationPersistenceMapper.domainToEntity(config));
        return CollectConfigurationPersistenceMapper.entityToDomain(entity);
    }

    @Override
    public void delete(UUID Id) {
        repository.deleteById(Id);
    }
}
