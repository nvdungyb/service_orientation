package com.java.postservice.messaging.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.java.postservice.enums.EPostStatus;
import com.java.postservice.utils.MicroTimestampToLocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseOutbox {
    private Long id;
    private String event_id;
    private EPostStatus event_type;
    private String payload;

    @JsonDeserialize(using = MicroTimestampToLocalDateTimeDeserializer.class)
    private LocalDateTime created_at;
}
