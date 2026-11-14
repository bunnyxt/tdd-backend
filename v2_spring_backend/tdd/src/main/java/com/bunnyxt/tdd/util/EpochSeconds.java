package com.bunnyxt.tdd.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class EpochSeconds {
    // PostgreSQL's finite timestamptz range ends before year 294277 (UTC).
    public static final long MAX_FINITE_SECOND = LocalDate.of(294277, 1, 1)
            .atStartOfDay().toEpochSecond(ZoneOffset.UTC) - 1;

    private EpochSeconds() { }

    public static OffsetDateTime utcDateTime(long seconds) {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneOffset.UTC);
    }
}
