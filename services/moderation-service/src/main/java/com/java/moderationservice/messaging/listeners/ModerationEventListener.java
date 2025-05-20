package com.java.moderationservice.messaging.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.moderationservice.service.ModerationService;
import com.java.moderationservice.service.RedisService;
import com.java.postservice.domain.PostCreatedEvent;
import com.java.postservice.domain.PostOutbox;
import com.java.postservice.enums.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class ModerationEventListener {
    private final ModerationService moderationService;

    public ModerationEventListener(ModerationService moderationService) {
        this.moderationService = moderationService;
    }

    /**
     * Tên method (bean) và tên binding phải khớp nhau theo format: <binding-name>-in-0 ⇨ Consumer<T> tên giống binding.
     * Mỗi Consumer<T> có thể gắn vào 1 topic duy nhất (trong config destination).
     *
     * @param objectMapper
     * @param redisService
     * @return
     */
    @Bean
    public Consumer<String> handlePostEvents(ObjectMapper objectMapper, RedisService redisService) {
        return message -> {
            log.info("Outbox event received: {}", message);
            try {
                PostOutbox event = objectMapper.readValue(message, PostOutbox.class);

                if (isProcessed(redisService, message, event)) return;

                if (event.getEvent_type().equals(EventType.CREATED.name())) {
                    handlePostCreatedEvent(objectMapper, event);
                } else {
                    log.info("We do not handle this kind of event yet: {}", event);
                }

            } catch (JsonProcessingException e) {
                log.error("Lỗi khi parse message: {}", e);
                // Có thể gửi vào một Dead Letter Queue hoặc lưu log DB nếu muốn
            }
        };
    }

    // handle idempotent
    private boolean isProcessed(RedisService redisService, String message, PostOutbox event) {
        if (redisService.exists(event.getEvent_id())) {
            log.info("This outbox event is processed: {}", event);
            return true;
        }

        redisService.save(event.getEvent_id(), message);
        return false;
    }

    private void handlePostCreatedEvent(ObjectMapper mapper, PostOutbox event) {
        try {
            PostCreatedEvent postCreatedEvent = mapper.readValue(event.getPayload(), PostCreatedEvent.class);
            log.info("Post event extracted: {}", event);

            if (moderationService.moderatePost(postCreatedEvent)) {
                log.info("Post moderation was successfully: {}", event);
                moderationService.saveApprovedOutbox(event);
            } else {
                log.info("Post moderation failed: {}", event);
                moderationService.saveRejectedOutbox(event);
            }
        } catch (JsonProcessingException ex) {
            log.error("Lỗi khi parse message: {}", ex);
        }
    }
}