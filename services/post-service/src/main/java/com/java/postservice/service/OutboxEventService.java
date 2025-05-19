package com.java.postservice.service;

import com.java.postservice.domain.ErrorOutbox;
import com.java.postservice.domain.PostOutbox;
import com.java.postservice.repository.ErrorOutboxRepository;
import com.java.postservice.repository.PostOutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxEventService {
    private final PostOutboxRepository postOutboxRepository;
    private final ErrorOutboxRepository errorOutboxRepository;

    public OutboxEventService(PostOutboxRepository postOutboxRepository, ErrorOutboxRepository errorOutboxRepository) {
        this.postOutboxRepository = postOutboxRepository;
        this.errorOutboxRepository = errorOutboxRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveInvalidAuthorEvent(ErrorOutbox postOutbox) {
        errorOutboxRepository.save(postOutbox);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void savePostEvent(PostOutbox postCreatedEvent) {
        postOutboxRepository.save(postCreatedEvent);
    }
}
