package com.example.meteoapporchestrator.business.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

/**
 *
 * @param Id
 * @param name
 * @param startDate
 * @param timespan in hours
 */
public record CollectConfigurationDTO(
        UUID Id,

        @NotNull
        String name,

        Instant startDate,

        @NotNull
        @Positive
        long timespan
) { }
