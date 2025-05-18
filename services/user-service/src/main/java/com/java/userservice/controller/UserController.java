package com.java.userservice.controller;

import com.java.userservice.domain.User;
import com.java.userservice.dto.ApiResponse;
import com.java.userservice.service.UserService;
import com.java.userservice.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable("id") Long id) {
        logger.info("Get user by id: {}", id);
        User user = userService.findById(id);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.FOUND.value())
                .message("User with id " + id + " found")
                .data(userMapper.toDto(user))
                .time(LocalDateTime.now())
                .build());
    }
}
