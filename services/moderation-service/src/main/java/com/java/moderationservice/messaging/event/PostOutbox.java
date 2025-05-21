package com.java.moderationservice.messaging.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.java.moderationservice.utils.MicroTimestampToLocalDateTimeDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostOutbox {
    private Long id;
    private String event_id;
    private String event_type;
    private String payload;
    private String aggregate_id;
    @JsonDeserialize(using = MicroTimestampToLocalDateTimeDeserializer.class)
    private LocalDateTime created_at;
}
