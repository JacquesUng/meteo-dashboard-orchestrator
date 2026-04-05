package com.example.meteoapporchestrator.controllers.adapter;

import com.example.meteoapporchestrator.business.core.tasks.INotificationTaskPlanner;
import com.example.meteoapporchestrator.business.ports.INotificationTaskServiceInterface;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationTaskAdapter implements INotificationTaskServiceInterface {
    private final INotificationTaskPlanner taskPlanner;

    public NotificationTaskAdapter(final INotificationTaskPlanner taskPlanner) {
        this.taskPlanner = taskPlanner;
    }

    @Override
    public void createTask(UUID configId) {
        taskPlanner.createTask(configId);
    }

    @Override
    public void cancelTask(UUID configId) {
        taskPlanner.cancelTask(configId);
    }
}
