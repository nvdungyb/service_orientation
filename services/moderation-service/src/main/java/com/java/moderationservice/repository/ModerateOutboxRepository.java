package com.java.moderationservice.repository;

import com.java.moderationservice.domain.ModerateOutbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModerateOutboxRepository extends CrudRepository<ModerateOutbox, Long> {
}
