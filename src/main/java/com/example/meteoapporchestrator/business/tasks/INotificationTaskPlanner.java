package com.example.meteoapporchestrator.business.tasks;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;

import java.util.List;
import java.util.UUID;

public interface INotificationTaskPlanner {
    void createTask(CollectConfigurationDTO dto);

    void cancelTask(UUID configId);

    public List<UUID> getAllActiveConfigIds();
}
