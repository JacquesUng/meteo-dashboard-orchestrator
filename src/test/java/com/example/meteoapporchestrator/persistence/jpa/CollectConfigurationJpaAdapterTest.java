package com.example.meteoapporchestrator.persistence.jpa;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.persistence.jpa.model.CollectConfigurationEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CollectConfigurationJpaAdapterTest {
    @InjectMocks
    private CollectConfigurationJpaAdapter adapter;

    @Mock
    private ICollectConfigurationRepository repository;


    @Test
    public void should_retrieveEmptyList_when_entityListEmpty() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        List<CollectConfiguration> configList = adapter.getAll();
        Assertions.assertEquals(0, configList.size());
    }

    @Test
    public void shouldRetrieveNonEmptyList_when_entityListNotEmpty() {
        List<CollectConfigurationEntity> entityList = Arrays.asList(
                new CollectConfigurationEntity("Config 1", Instant.parse("2026-01-01T00:00:00Z"), 1),
                new CollectConfigurationEntity("Config 2", null, 2)
        );
        entityList.getFirst().setId(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        entityList.getLast().setId(UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"));
        Mockito.when(repository.findAll()).thenReturn(entityList);
        List<CollectConfiguration> configList = adapter.getAll();
        Assertions.assertEquals(2, configList.size());
        Assertions.assertEquals(
                new CollectConfiguration(
                        UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                        "Config 1",
                        Instant.parse("2026-01-01T00:00:00Z"),
                        1
                ),
                configList.getFirst()
        );
        Assertions.assertEquals(
                new CollectConfiguration(
                        UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"),
                        "Config 2",
                        null,
                        2
                ),
                configList.get(1)
        );
    }

    @Test
    public void should_returnOne_when_resourceFound() {
        CollectConfigurationEntity entity = new CollectConfigurationEntity("Config 1", Instant.parse("2026-01-01T00:00:00Z"), 1);
        UUID Id = UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b");
        entity.setId(Id);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(
                new CollectConfiguration(Id, "Config 1", Instant.parse("2026-01-01T00:00:00Z"), 1),
                adapter.getOne(Id)
        );
    }

    @Test
    public void should_throwException_when_resourceNotFound() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> adapter.getOne(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"))
        );
    }
}
