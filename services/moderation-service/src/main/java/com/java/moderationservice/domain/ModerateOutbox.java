package com.java.moderationservice.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.java.postservice.enums.EPostStatus;
import com.java.postservice.utils.MicroTimestampToLocalDateTimeDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModerateOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "event_id")
    private String event_id;

    @Column(nullable = false, name = "event_type")
    @Enumerated(value = EnumType.STRING)
    private EPostStatus event_type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false, name = "created_at")
    @JsonDeserialize(using = MicroTimestampToLocalDateTimeDeserializer.class)
    private LocalDateTime created_at;
}
