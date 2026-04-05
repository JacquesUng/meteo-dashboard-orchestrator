package com.example.meteoapporchestrator.business.core.collectconfigs;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.business.ports.ICollectConfigurationPersistenceService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThatList;

@ExtendWith(MockitoExtension.class)
public class CollectConfigurationManagerTest {
    @InjectMocks
    private CollectConfigurationManager manager;

    @Mock
    private ICollectConfigurationPersistenceService persistenceService;

    private void mockGetAll() {
        Mockito.doReturn(Arrays.asList(
                new CollectConfiguration(
                        UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                        "Config name 1",
                        Instant.parse("2026-02-01T10:00:00.000Z"),
                        2
                ),
                new CollectConfiguration(
                        UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"),
                        "Config name 2",
                        Instant.parse("2026-02-01T10:00:00.000Z"),
                        1
                )
        )).when(persistenceService).getAll();
    }

    private void mockGetOne() {
        Mockito.doAnswer(invocation -> {
            UUID configId = invocation.getArgument(0);
            return new CollectConfiguration(
                    configId,
                    "Config name",
                    Instant.parse("2026-02-01T10:00:00.000Z"),
                    2
            );
        }).when(persistenceService).getOne(Mockito.any(UUID.class));
    }

    private void mockSaveOne() {
        Mockito.doAnswer(invocation -> {
                    CollectConfiguration toSave = invocation.getArgument(0);
                    return new CollectConfiguration(
                            UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                            toSave.name(),
                            toSave.startDate(),
                            toSave.timespan()
                    );
                })
                .when(persistenceService).save(Mockito.any(CollectConfiguration.class));
    }

    @Test
    public void should_getAll() {
        mockGetAll();
        assertThatList(manager.getAll()).containsExactly(
                new CollectConfiguration(
                        UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                        "Config name 1",
                        Instant.parse("2026-02-01T10:00:00.000Z"),
                        2
                ),
                new CollectConfiguration(
                        UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"),
                        "Config name 2",
                        Instant.parse("2026-02-01T10:00:00.000Z"),
                        1
                )
         );
    }

    @Test
    public void should_get_one() {
        mockGetOne();
        assertEquals(
                new CollectConfiguration(
                        UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"),
                        "Config name",
                        Instant.parse("2026-02-01T10:00:00.000Z"),
                        2
                ),
                persistenceService.getOne(UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"))
        );
    }

    @Test
    public void should_returnEntity_when_savingOne() {
        mockSaveOne();
        assertEquals(
                new CollectConfiguration(
                        UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                        "Config name",
                        Instant.parse("2026-02-01T10:00:00.000Z"),
                        2
                ),
                persistenceService.save(new CollectConfiguration(
                        null,
                        "Config name",
                        Instant.parse("2026-02-01T10:00:00.000Z"),
                        2
                ))
        );
    }
}
