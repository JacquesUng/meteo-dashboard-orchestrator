package com.example.meteoapporchestrator.business.ports;

import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;

import java.util.UUID;

public interface INotificationTaskServiceInterface {
    void createTask(UUID configId);

    void cancelTask(UUID configId);
}
