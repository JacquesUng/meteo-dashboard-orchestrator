package com.example.meteoapporchestrator.controllers;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.controllers.adapter.CollectConfigurationControllerMapper;
import com.example.meteoapporchestrator.controllers.model.CollectConfigurationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

public class CollectConfigurationControllerMapperTest {
    @Test
    public void should_MapDtoToDomainObject() {
        CollectConfigurationDto dto = new CollectConfigurationDto(
                UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                "Config name",
                Instant.parse("2026-02-01T10:00:00.000Z"),
                2
        );

        CollectConfiguration config = CollectConfigurationControllerMapper.dtoToDomain(dto);
        Assertions.assertEquals("732f8381-9783-4c46-9978-56ab0d8f7c8b", config.Id().toString());
        Assertions.assertEquals("Config name", config.name());
        Assertions.assertEquals(Instant.parse("2026-02-01T10:00:00.000Z"), config.startDate());
        Assertions.assertEquals(2, config.timespan());
    }

    @Test
    public void should_mapDomainObjectToDto() {
        CollectConfiguration config = new CollectConfiguration(
                UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                "Config name",
                Instant.parse("2026-02-01T10:00:00.000Z"),
                2
        );

        CollectConfigurationDto dto = CollectConfigurationControllerMapper.domainToDto(config);
        Assertions.assertEquals("732f8381-9783-4c46-9978-56ab0d8f7c8b", dto.Id().toString());
        Assertions.assertEquals("Config name", dto.name());
        Assertions.assertEquals(Instant.parse("2026-02-01T10:00:00.000Z"), dto.startDate());
        Assertions.assertEquals(2, dto.timespan());
    }
}
