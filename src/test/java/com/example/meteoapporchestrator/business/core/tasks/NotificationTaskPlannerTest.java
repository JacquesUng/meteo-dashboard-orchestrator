package com.example.meteoapporchestrator.business.core.tasks;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class NotificationTaskPlannerTest {
    @InjectMocks
    private NotificationTaskPlannerImpl taskPlanner;

    @Mock
    private TaskScheduler taskScheduler;

    @Test
    public void should_populateFutureMap_when_creatingTask() {
        ScheduledFuture<?> mockScheduledFuture = mock(ScheduledFuture.class);
        Mockito
                .doReturn(mockScheduledFuture)
                .when(taskScheduler).scheduleAtFixedRate(Mockito.any(Runnable.class), Mockito.any(Instant.class), Mockito.any(Duration.class));

        CollectConfiguration config = new CollectConfiguration(
                UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                "Config name",
                Instant.parse("2026-02-01T10:00:00.000Z"),
                2
        );
        taskPlanner.createTask(config);

        List<UUID> activeConfigIdList = taskPlanner.getAllActiveConfigIds();
        Mockito
                .verify(taskScheduler, times(1))
                .scheduleAtFixedRate(Mockito.any(Runnable.class), Mockito.any(Instant.class), Mockito.any(Duration.class));
        Assertions.assertEquals(1, activeConfigIdList.size());
        Assertions.assertEquals("732f8381-9783-4c46-9978-56ab0d8f7c8b", activeConfigIdList.getFirst().toString());
    }
}
