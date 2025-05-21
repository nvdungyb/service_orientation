package com.java.moderationservice.http;

import com.java.moderationservice.dto.ApiResponse;
import com.java.moderationservice.dto.response.PostResponseDto;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PostServiceClient {

    private final PostClient postClient;

    public PostServiceClient(PostClient postClient) {
        this.postClient = postClient;
    }

    // todo: chưa ổn.

    /**
     * @param postId
     * @return PostResponseDto if data is present and Post service is not interrupted otherwise Optional.empty()
     */
    public Optional<PostResponseDto> getPostById(Long postId) {
        try {
            ResponseEntity<ApiResponse<PostResponseDto>> response = postClient.getPostById(postId);
            return Optional.ofNullable(response.getBody().getData());
        } catch (FeignException.FeignClientException ex) {
            log.error(ex.getMessage());
        } catch (FeignException ex) {
            log.error(ex.getMessage());
        }
        return Optional.empty();
    }
}
