package com.example.meteoapporchestrator.business.core.tasks;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;

import java.util.List;
import java.util.UUID;

public interface INotificationTaskPlanner {
    void createTask(CollectConfiguration config);

    void cancelTask(UUID configId);

    public List<UUID> getAllActiveConfigIds();
}
