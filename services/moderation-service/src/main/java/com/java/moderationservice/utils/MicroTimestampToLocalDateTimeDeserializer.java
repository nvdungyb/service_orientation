package com.java.moderationservice.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MicroTimestampToLocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    public MicroTimestampToLocalDateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        long timestampMicros = p.getLongValue();
        // Convert microseconds to seconds and nanoseconds
        long seconds = timestampMicros / 1_000_000;
        long nanos = (timestampMicros % 1_000_000) * 1_000;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds, nanos), ZoneOffset.UTC);
    }
}