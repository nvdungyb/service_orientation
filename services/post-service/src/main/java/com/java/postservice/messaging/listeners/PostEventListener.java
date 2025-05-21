package com.java.postservice.messaging.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.postservice.domain.PostCreatedEvent;
import com.java.postservice.messaging.events.BaseOutbox;
import com.java.postservice.service.PostService;
import com.java.postservice.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class PostEventListener {
    private final String POST_REDIS_KEY = "Moderated:";
    private final PostService postService;

    public PostEventListener(PostService postService) {
        this.postService = postService;
    }

    @Bean
    public Consumer<String> handleEvents(ObjectMapper objectMapper, RedisService redisService) {
        return message -> {
            log.info("Outbox event received: {}", message);
            try {
                BaseOutbox event = objectMapper.readValue(message, BaseOutbox.class);

                if (isProcessed(redisService, message, event)) return;

                handlePostEvent(objectMapper, event);

            } catch (JsonProcessingException ex) {
                log.error("Lỗi khi parse message: {}", message, ex);
            }
        };
    }

    // event trong payload la PostCreatedEvent
    private void handlePostEvent(ObjectMapper objectMapper, BaseOutbox outbox) {
        try {
            PostCreatedEvent event = objectMapper.readValue(outbox.getPayload(), PostCreatedEvent.class);
            postService.updatePostStatus(event.postId(), outbox.getEvent_type());
        } catch (JsonProcessingException ex) {
            log.error("Lỗi khi parse message: {}", ex);
        }
    }

    private boolean isProcessed(RedisService redisService, String message, BaseOutbox event) {
        if (redisService.exists(getKey(event.getEvent_id()))) {
            log.info("This outbox event is processed: {}", event);
            return true;
        }

        redisService.save(getKey(event.getEvent_id()), message);
        return false;
    }

    private String getKey(String event_id) {
        return POST_REDIS_KEY + event_id;
    }
}
