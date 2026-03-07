package com.example.meteoapporchestrator.business.model;

import java.time.Instant;
import java.util.UUID;

/**
 *
 * @param Id
 * @param name
 * @param startDate
 * @param timespan in hours
 */
public record CollectConfiguration(
        UUID Id,

        String name,

        Instant startDate,

        long timespan
) { }
