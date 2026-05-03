package com.example.meteoapporchestrator.business.ports;

import java.util.List;
import java.util.UUID;

public interface INotificationTaskServiceInterface {
    void createTask(UUID configId);

    void cancelTask(UUID configId);

    boolean isActive(UUID configId);

    List<UUID> getAllActiveConfigIds();
}
