package com.example.meteoapporchestrator.controllers.model;

import java.time.Instant;
import java.util.UUID;

public record CollectConfigurationResponseDto (
        UUID Id,
        String name,
        Instant startDate,
        long timespan,
        boolean active
) {
}
