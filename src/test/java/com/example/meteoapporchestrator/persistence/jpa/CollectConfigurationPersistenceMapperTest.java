package com.example.meteoapporchestrator.persistence.jpa;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;
import com.example.meteoapporchestrator.persistence.jpa.model.CollectConfigurationEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

public class CollectConfigurationPersistenceMapperTest {
    @Test
    public void should_mapEntityToDomainObject() {
        CollectConfigurationEntity entity = new CollectConfigurationEntity(
                "Config name",
                Instant.parse("2026-02-01T10:00:00.000Z"),
                2
        );
        entity.setId(UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"));
        CollectConfiguration config = CollectConfigurationPersistenceMapper.entityToDomain(entity);

        Assertions.assertEquals("732f8381-9783-4c46-9978-56ab0d8f7c8b", config.Id().toString());
        Assertions.assertEquals("Config name", config.name());
        Assertions.assertEquals(Instant.parse("2026-02-01T10:00:00.000Z"), config.startDate());
        Assertions.assertEquals(2, config.timespan());
    }

    @Test
    public void should_mapDomainObjectToEntity() {
        CollectConfiguration config = new CollectConfiguration(
                UUID.fromString("732f8381-9783-4c46-9978-56ab0d8f7c8b"),
                "Config name",
                Instant.parse("2026-02-01T10:00:00.000Z"),
                2
        );
        CollectConfigurationEntity entity = CollectConfigurationPersistenceMapper.domainToEntity(config);

        Assertions.assertEquals("732f8381-9783-4c46-9978-56ab0d8f7c8b", entity.getId().toString());
        Assertions.assertEquals("Config name", entity.getName());
        Assertions.assertEquals(Instant.parse("2026-02-01T10:00:00.000Z"), entity.getStartDate());
        Assertions.assertEquals(2, entity.getTimespan());
    }
}
