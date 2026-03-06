package com.example.meteoapporchestrator.business.tasks;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Future;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class NotificationTaskPlannerImpl implements INotificationTaskPlanner {
    private final TaskScheduler taskScheduler;

    private final Map<String, Future<?>> futureMap;

    public NotificationTaskPlannerImpl(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        this.futureMap = new HashMap<>();
    }

    public void createTask(CollectConfigurationDTO dto) {
        Runnable task = new MessageTask(dto);
        Future<?> future = taskScheduler.scheduleAtFixedRate(task, dto.startDate(), Duration.of(dto.timespan(), ChronoUnit.HOURS));
        futureMap.put(dto.Id().toString(), future);
    }

    public void cancelTask(UUID configId) {
        if (!futureMap.containsKey(configId.toString())) {
            String errorMessage = String.format("Configuration with ID %s has no associated task currently scheduled", configId);
            throw new ResponseStatusException(NOT_FOUND, errorMessage);
        }
        futureMap.get(configId.toString()).cancel(false);
        futureMap.remove(configId.toString());
    }

    public List<UUID> getAllActiveConfigIds() {
        return futureMap.keySet().stream().map(UUID::fromString).toList();
    }
}
