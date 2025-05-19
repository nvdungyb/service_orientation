package com.java.postservice.domain;

import java.time.LocalDateTime;

public record PostRejectedEvent(Long postId, String reason, LocalDateTime rejectedAt) implements PostEvent {
    public PostRejectedEvent(Long postId, String reason) {
        this(postId, reason, LocalDateTime.now());
    }
}
