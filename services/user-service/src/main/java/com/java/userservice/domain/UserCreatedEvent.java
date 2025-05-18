package com.java.userservice.domain;

import com.java.userservice.enums.ActionEnum;

public record UserCreatedEvent(Long userId, String userName, String userEmail, ActionEnum action) implements UserEvent {
    public UserCreatedEvent(Long userId, String userName, String userEmail) {
        this(userId, userName, userEmail, ActionEnum.CREATED);
    }
}
