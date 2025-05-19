package com.java.postservice.domain;

import com.java.postservice.enums.EPostStatus;

import java.time.LocalDateTime;

public record PostVisibilityChangedEvent(Long postId, EPostStatus status, LocalDateTime changedAt) implements PostEvent {
    public PostVisibilityChangedEvent(Long postId, EPostStatus status) {
        this(postId, status, LocalDateTime.now());
    }
}
