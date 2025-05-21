package com.java.moderationservice.http;

import com.java.moderationservice.dto.ApiResponse;
import com.java.moderationservice.dto.response.PostResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-client", url = "${post.service.url}")
public interface PostClient {
    @GetMapping("/posts/{id}")
    ResponseEntity<ApiResponse<PostResponseDto>> getPostById(@PathVariable("id") Long postId);
}
