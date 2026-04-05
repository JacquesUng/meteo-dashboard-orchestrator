package com.example.meteoapporchestrator.business.core.tasks;

import java.util.List;
import java.util.UUID;

public interface INotificationTaskPlanner {
    void createTask(UUID configId);

    void cancelTask(UUID configId);

    boolean isActive(UUID configId);

    List<UUID> getAllActiveConfigIds();
}
