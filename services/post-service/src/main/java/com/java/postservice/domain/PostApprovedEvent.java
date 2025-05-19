package com.java.postservice.domain;

import java.time.LocalDateTime;

public record PostApprovedEvent(Long postId, LocalDateTime approvedAt, Long approvedBy) implements PostEvent {
    public PostApprovedEvent(Long postId, Long approvedBy) {
        this(postId, LocalDateTime.now(), approvedBy);
    }
}
