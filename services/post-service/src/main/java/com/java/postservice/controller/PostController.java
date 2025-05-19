package com.java.postservice.controller;

import com.java.postservice.domain.Post;
import com.java.postservice.dto.ApiResponse;
import com.java.postservice.dto.request.PostCreationDto;
import com.java.postservice.service.PostService;
import com.java.postservice.utils.PostMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @PostMapping("/posts")
    public ResponseEntity<?> addPost(@Valid @RequestBody PostCreationDto postCreationDto, HttpServletRequest request) {
        Post post = postService.create(postCreationDto);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Post created")
                .data(post)
                .time(LocalDateTime.now())
                .path(request.getRequestURI())
                .build()
        );
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<?>> getPostById(@PathVariable Long id, HttpServletRequest request) {
        Post post = postService.findPostById(id);

        return ResponseEntity.ok().body(ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Retrieve post sucessully")
                .data(postMapper.toResponseDto(post))
                .time(LocalDateTime.now())
                .path(request.getRequestURI())
                .build());
    }
}
