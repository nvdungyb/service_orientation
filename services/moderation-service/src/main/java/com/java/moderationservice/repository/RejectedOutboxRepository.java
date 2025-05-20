package com.java.moderationservice.repository;

import com.java.moderationservice.domain.RejectedOutbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectedOutboxRepository extends CrudRepository<RejectedOutbox, Long> {
}
