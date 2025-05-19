package com.java.postservice.repository;

import com.java.postservice.domain.PostOutbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostOutboxRepository extends CrudRepository<PostOutbox, String> {
}
