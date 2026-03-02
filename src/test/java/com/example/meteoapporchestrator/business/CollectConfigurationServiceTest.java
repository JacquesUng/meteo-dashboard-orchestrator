package com.example.meteoapporchestrator.business;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;
import com.example.meteoapporchestrator.persistence.jpa.model.CollectConfiguration;
import com.example.meteoapporchestrator.persistence.jpa.CollectConfigurationJpaServiceImpl;
import com.example.meteoapporchestrator.persistence.jpa.ICollectConfigurationRepository;
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
public class CollectConfigurationServiceTest {
    @InjectMocks
    private CollectConfigurationJpaServiceImpl collectionConfigurationService;

    @Mock
    private ICollectConfigurationRepository repository;


    @Test
    public void should_retrieveEmptyDtoList_when_entityListEmpty() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        List<CollectConfigurationDTO> dtoList = collectionConfigurationService.getAll();
        Assertions.assertEquals(0, dtoList.size());
    }

    @Test
    public void shouldRetrieveNonEmptyDtoList_when_entityListNotEmpty() {
        List<CollectConfiguration> list = Arrays.asList(
                new CollectConfiguration("Config 1", Instant.parse("2026-01-01T00:00:00Z"), 1),
                new CollectConfiguration("Config 2", null, 0)
        );
        list.getFirst().setId(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        list.getLast().setId(UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"));
        Mockito.when(repository.findAll()).thenReturn(list);
        List<CollectConfigurationDTO> dtoList = collectionConfigurationService.getAll();
        Assertions.assertEquals(2, dtoList.size());
        Assertions.assertEquals(
                new CollectConfigurationDTO(
                        UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                        "Config 1",
                        Instant.parse("2026-01-01T00:00:00Z"),
                        1
                ),
                dtoList.getFirst()
        );
        Assertions.assertEquals(
                new CollectConfigurationDTO(
                        UUID.fromString("944e5a4f-fc2f-419c-9613-7bc8636e92ad"),
                        "Config 2",
                        null,
                        2
                ),
                dtoList.get(1)
        );
    }

    @Test
    public void should_returnOne_when_resourceFound() {
        CollectConfiguration entity = new CollectConfiguration("Config 1", Instant.parse("2026-01-01T00:00:00Z"), 1);
        UUID Id = UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b");
        entity.setId(Id);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(entity));
        Assertions.assertEquals(
                new CollectConfigurationDTO(Id, "Config 1", Instant.parse("2026-01-01T00:00:00Z"), 1),
                collectionConfigurationService.getOne(Id)
        );
    }

    @Test
    public void should_throwException_when_resourceNotFound() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(
                ResponseStatusException.class,
                () -> collectionConfigurationService.getOne(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b")))
        ;
    }
}
