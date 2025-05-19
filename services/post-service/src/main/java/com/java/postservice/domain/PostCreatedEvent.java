package com.java.postservice.domain;

import com.java.postservice.enums.EPostStatus;

import java.time.LocalDateTime;

public record PostCreatedEvent(Long postId, Long authorId, LocalDateTime createdAt,
                               EPostStatus postStatus) implements PostEvent {
    public PostCreatedEvent(Long postId, Long authorId) {
        this(postId, authorId, LocalDateTime.now(), EPostStatus.PENDING);
    }
}
