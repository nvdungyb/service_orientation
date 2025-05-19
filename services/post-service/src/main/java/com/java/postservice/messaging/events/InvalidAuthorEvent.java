package com.java.postservice.messaging.events;

import java.time.LocalDateTime;

public record InvalidAuthorEvent(Long authorId, String message,
                                 LocalDateTime occurredAt) {

    public InvalidAuthorEvent(Long authorId, String message) {
        this(authorId, message, LocalDateTime.now());
    }
}
