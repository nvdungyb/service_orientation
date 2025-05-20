package com.java.postservice.repository;

import com.java.postservice.domain.Post;
import com.java.postservice.enums.EPostStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {
    @Modifying
    @Query("UPDATE Post p SET p.status = ?2 WHERE p.id = ?1")
    void updatePostEvent(Long postId, EPostStatus eventStatus);
}
