package com.example.meteoapporchestrator.controllers.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

public record CollectConfigurationDto(
        UUID Id,

        @NotNull
        String name,

        Instant startDate,

        @NotNull
        @Positive
        long timespan
) {
}
