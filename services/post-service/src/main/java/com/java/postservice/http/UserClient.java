package com.java.postservice.http;

import com.java.postservice.dto.ApiResponse;
import com.java.postservice.dto.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserClient {

    @GetMapping("/users/{id}")
    ResponseEntity<ApiResponse<UserInfo>> getAuthorById(@PathVariable("id") Long id);
}
