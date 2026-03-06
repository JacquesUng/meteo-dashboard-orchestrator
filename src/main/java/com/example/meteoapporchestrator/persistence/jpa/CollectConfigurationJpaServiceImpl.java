package com.example.meteoapporchestrator.persistence.jpa;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;
import com.example.meteoapporchestrator.business.services.ICollectConfigurationService;
import com.example.meteoapporchestrator.persistence.jpa.model.CollectConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CollectConfigurationJpaServiceImpl implements ICollectConfigurationService {
    private final ICollectConfigurationRepository repository;

    public CollectConfigurationJpaServiceImpl(final ICollectConfigurationRepository repository) {
        this.repository = repository;
    }

    public List<CollectConfigurationDTO> getAll() {
        List<CollectConfiguration> collectConfigurationList = repository.findAll();
        return collectConfigurationList.stream().map(CollectConfigurationMapper::entityToDto).toList();
    }

    public CollectConfigurationDTO getOne(UUID Id) {
        Optional<CollectConfiguration> entity = repository.findById(Id);
        if (entity.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
        return CollectConfigurationMapper.entityToDto(entity.get());
    }

    public CollectConfigurationDTO save(CollectConfigurationDTO dto) {
        CollectConfiguration entity = repository.save(CollectConfigurationMapper.dtoToEntity(dto));
        return CollectConfigurationMapper.entityToDto(entity);
    }
}
