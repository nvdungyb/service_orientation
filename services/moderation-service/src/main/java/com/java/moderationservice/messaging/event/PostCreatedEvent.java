package com.java.moderationservice.messaging.event;

import com.java.moderationservice.enums.EPostStatus;

import java.time.LocalDateTime;

public record PostCreatedEvent(Long postId, Long authorId, LocalDateTime createdAt,
                               EPostStatus postStatus) {
}
