package com.java.moderationservice.repository;

import com.java.moderationservice.domain.ApprovedOutbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovedOutboxRepository extends CrudRepository<ApprovedOutbox, Long> {
}
