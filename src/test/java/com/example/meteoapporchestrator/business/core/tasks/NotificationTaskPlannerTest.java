package com.example.meteoapporchestrator.business.core.tasks;

import com.example.meteoapporchestrator.business.core.collectconfigs.ICollectConfigurationManager;
import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class NotificationTaskPlannerTest {
    @InjectMocks
    private NotificationTaskPlannerImpl taskPlanner;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private ICollectConfigurationManager collectConfigurationManager;

    @BeforeEach
    public void init() {
        ScheduledFuture<?> mockScheduledFuture = mock(ScheduledFuture.class);
        Mockito
                .lenient()
                .doReturn(mockScheduledFuture)
                .when(taskScheduler)
                .scheduleAtFixedRate(Mockito.any(Runnable.class), Mockito.any(Instant.class), Mockito.any(Duration.class));

        Mockito.lenient().doAnswer(invocation -> {
            UUID configId = invocation.getArgument(0);
            return new CollectConfiguration(
                    configId,
                    "Config name",
                    Instant.parse("2026-02-01T10:00:00.000Z"),
                    2
            );
        }).when(collectConfigurationManager).getOne(Mockito.any(UUID.class));
    }

    @Test
    public void should_taskNotBeActive_before_created() {
        Assertions.assertFalse(taskPlanner.isActive(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b")));
    }

    @Test
    public void should_taskBeActive_after_created() {
        taskPlanner.createTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        Assertions.assertTrue(taskPlanner.isActive(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b")));
    }

    @Test
    public void should_throw_when_creatingAlreadyExistingTask() {
        taskPlanner.createTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        ResponseStatusException exception = Assertions.assertThrowsExactly(
                ResponseStatusException.class,
                () -> taskPlanner.createTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"))
        );
        Assertions.assertEquals(
                "Configuration with ID 732f8381-9783-4c46-9978-56ab0d8f7c8b has already an associated task currently scheduled",
                exception.getReason()
        );
        Assertions.assertEquals(CONFLICT, exception.getStatusCode());
    }

    @Test
    public void should_taskNotBeActive_after_cancelled() {
        taskPlanner.createTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        taskPlanner.cancelTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        Assertions.assertFalse(taskPlanner.isActive(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b")));
    }

    @Test
    public void should_populateFutureMap_when_creatingTask() {
        taskPlanner.createTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));

        List<UUID> activeConfigIdList = taskPlanner.getAllActiveConfigIds();
        Mockito
                .verify(taskScheduler, times(1))
                .scheduleAtFixedRate(Mockito.any(Runnable.class), Mockito.any(Instant.class), Mockito.any(Duration.class));
        Assertions.assertEquals(1, activeConfigIdList.size());
        Assertions.assertEquals("732f8381-9783-4c46-9978-56ab0d8f7c8b", activeConfigIdList.getFirst().toString());
    }

    @Test
    public void should_mapBeEmpty_after_cancelingTask() {
        taskPlanner.createTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        taskPlanner.cancelTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        List<UUID> activeConfigIdListAfterRemoval = taskPlanner.getAllActiveConfigIds();
        Assertions.assertEquals(0, activeConfigIdListAfterRemoval.size());
    }

    @Test
    public void should_throw_when_cancelingNonExistingTask() {
        taskPlanner.createTask(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        ResponseStatusException exception = Assertions.assertThrowsExactly(
                ResponseStatusException.class,
                () -> taskPlanner.cancelTask(UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"))
        );
        Assertions.assertEquals(
                "Configuration with ID 944e5a4f-fc2f-419c-9613-7bc8636e92ad has no associated task currently scheduled",
                exception.getReason()
        );
        Assertions.assertEquals(NOT_FOUND, exception.getStatusCode());
    }
}
