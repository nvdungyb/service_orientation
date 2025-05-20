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
@Table(name = "approved_outbox")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "event_id")
    private String event_id;

    @Column(nullable = false, name = "event_type")
    @Enumerated(value = EnumType.STRING)
    private EPostStatus event_type = EPostStatus.APPROVED;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false, name = "created_at")
    @JsonDeserialize(using = MicroTimestampToLocalDateTimeDeserializer.class)
    private LocalDateTime created_at;
}
