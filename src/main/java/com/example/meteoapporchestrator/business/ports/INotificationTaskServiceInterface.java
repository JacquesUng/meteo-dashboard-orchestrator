package com.example.meteoapporchestrator.business.ports;

import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;

import java.util.UUID;

public interface INotificationTaskServiceInterface {
    void createTask(CollectConfigurationDto dto);

    void cancelTask(UUID configId);
}
