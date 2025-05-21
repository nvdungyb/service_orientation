package com.java.userservice.messaging.publishers;

import com.java.userservice.domain.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(UserEventPublisher.class);
    private final StreamBridge streamBridge;

    public UserEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishUserEvent(UserEvent userEvent) {
        logger.info("Publishing user event: " + userEvent);

        streamBridge.send("output-out-0", MessageBuilder.withPayload(userEvent).build());
    }
}
