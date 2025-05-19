package com.java.postservice.repository;

import com.java.postservice.domain.ErrorOutbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorOutboxRepository extends CrudRepository<ErrorOutbox, Long> {
}
