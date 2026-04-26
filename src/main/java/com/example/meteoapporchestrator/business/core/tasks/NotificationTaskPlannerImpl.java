package com.example.meteoapporchestrator.business.core.tasks;

import com.example.meteoapporchestrator.business.core.collectconfigs.ICollectConfigurationManager;
import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Future;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class NotificationTaskPlannerImpl implements INotificationTaskPlanner {
    private final TaskScheduler taskScheduler;

    private final ICollectConfigurationManager collectConfigurationManager;

    private final Map<String, Future<?>> futureMap;

    public NotificationTaskPlannerImpl(TaskScheduler taskScheduler, ICollectConfigurationManager collectConfigurationManager) {
        this.taskScheduler = taskScheduler;
        this.collectConfigurationManager = collectConfigurationManager;
        this.futureMap = new HashMap<>();
    }

    @Override
    public void createTask(UUID configId) {
        if (futureMap.containsKey(configId.toString())) {
            String errorMessage = String.format("Configuration with ID %s has already an associated task currently scheduled", configId);
            throw new ResponseStatusException(CONFLICT, errorMessage);
        }
        CollectConfiguration config = collectConfigurationManager.getOne(configId);
        Runnable task = new MessageTask(config);
        Instant startDate = config.startDate() == null ? Instant.now() : config.startDate();
        Future<?> future = taskScheduler.scheduleAtFixedRate(task, startDate, Duration.of(config.timespan(), ChronoUnit.HOURS));
        futureMap.put(config.Id().toString(), future);
    }

    @Override
    public void cancelTask(UUID configId) {
        if (!futureMap.containsKey(configId.toString())) {
            String errorMessage = String.format("Configuration with ID %s has no associated task currently scheduled", configId);
            throw new ResponseStatusException(NOT_FOUND, errorMessage);
        }
        futureMap.get(configId.toString()).cancel(false);
        futureMap.remove(configId.toString());
    }

    @Override
    public boolean isActive(UUID configId) {
        return futureMap.containsKey(configId.toString());
    }

    @Override
    public List<UUID> getAllActiveConfigIds() {
        return futureMap.keySet().stream().map(UUID::fromString).toList();
    }
}
