package com.example.meteoapporchestrator.business.services;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;

import java.util.List;
import java.util.UUID;

public interface ICollectConfigurationService {
    List<CollectConfigurationDTO> getAll();

    CollectConfigurationDTO getOne(UUID Id);

    CollectConfigurationDTO save(CollectConfigurationDTO dto);
}
