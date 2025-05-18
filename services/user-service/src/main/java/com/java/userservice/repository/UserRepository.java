package com.java.userservice.repository;

import com.java.userservice.domain.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    List<User> email(@Email String email);

    Optional<User> findByEmail(@Email String email);
}
