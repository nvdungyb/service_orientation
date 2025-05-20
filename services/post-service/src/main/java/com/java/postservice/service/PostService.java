package com.java.postservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.postservice.domain.ErrorOutbox;
import com.java.postservice.domain.Post;
import com.java.postservice.domain.PostCreatedEvent;
import com.java.postservice.domain.PostOutbox;
import com.java.postservice.dto.ApiResponse;
import com.java.postservice.dto.request.PostCreationDto;
import com.java.postservice.dto.response.UserInfo;
import com.java.postservice.dto.response.ValidateUserResponse;
import com.java.postservice.enums.EPostStatus;
import com.java.postservice.enums.EventType;
import com.java.postservice.exception.AuthorValidationException;
import com.java.postservice.exception.PostCreationException;
import com.java.postservice.http.UserClient;
import com.java.postservice.messaging.events.InvalidAuthorEvent;
import com.java.postservice.repository.PostOutboxRepository;
import com.java.postservice.repository.PostRepository;
import com.java.postservice.utils.PostMapper;
import com.java.userservice.enums.Erole;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final UserClient userClient;
    private final ObjectMapper objectMapper;
    private final PostOutboxRepository postOutboxRepository;
    private final OutboxEventService outboxEventService;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, UserClient userClient, ObjectMapper objectMapper, PostOutboxRepository postOutboxRepository, OutboxEventService outboxEventService, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userClient = userClient;
        this.objectMapper = objectMapper;
        this.postOutboxRepository = postOutboxRepository;
        this.outboxEventService = outboxEventService;
        this.postMapper = postMapper;
    }

    // todo: khi tự quản lý uuid, thì khi lưu entity, JPA phải thực hiện select nếu tồn tại thì update, còn không thì mới lưu.
    // giải pháp có thể là tự sinh uuid và thực hiện persist thủ công.
    @Transactional
    public Post create(PostCreationDto postCreationDto) {
        try {
            Long authorId = postCreationDto.getAuthorId();

            ValidateUserResponse userResponse = checkValidAuthor(authorId);
            if (!userResponse.getStatus()) {
                String errorMessage = String.format("Unable to create post because, %s", userResponse.getMessage());
                ErrorOutbox invalidAuthorEvent = ErrorOutbox.builder()
                        .eventType(EventType.INVALID_AUTHOR.name())
                        .payload(objectMapper.writeValueAsString(new InvalidAuthorEvent(authorId, errorMessage)))
                        .errorMessage(errorMessage)
                        .createdAt(LocalDateTime.now())
                        .build();

                outboxEventService.saveInvalidAuthorEvent(invalidAuthorEvent);
                throw new AuthorValidationException(errorMessage);
            }

            Post post = postMapper.toEntity(postCreationDto);

            // todo: attachments lưu trên cloud, đây là việc của fontend, ta chỉ lưu url

            Post savedPost = postRepository.save(post);

            PostOutbox postCreatedEvent = PostOutbox.builder()
                    .event_type(EventType.CREATED.name())
                    .event_id(generateEventId())
                    .payload(objectMapper.writeValueAsString(new PostCreatedEvent(savedPost.getId(), authorId)))
                    .aggregate_id(savedPost.getId().toString())
                    .created_at(LocalDateTime.now())
                    .build();

            outboxEventService.savePostEvent(postCreatedEvent);

            return savedPost;
        } catch (JsonProcessingException e) {
            throw new PostCreationException("Failed to serialize event payload", e);
        } catch (Exception e) {
            throw new PostCreationException(e.getMessage());
        }
    }

    private String generateEventId() {
        return UUID.randomUUID().toString();
    }

    private ValidateUserResponse checkValidAuthor(Long authorId) {
        try {
            ResponseEntity<ApiResponse<UserInfo>> response = userClient.getAuthorById(authorId);

            if (response.getBody() == null) {
                return new ValidateUserResponse(false, "Author does not exist");
            }

            UserInfo userInfo = response.getBody().getData();

            if (!userInfo.getIsActive()) {
                return new ValidateUserResponse(false, String.format("Author is inactive: %d", authorId));
            }

            if (!userInfo.getEroles().contains(Erole.END_USER)) {
                return new ValidateUserResponse(false, String.format("Author does not have posting permission: %d", authorId));
            }

            return new ValidateUserResponse(true, "OK");
        } catch (FeignException.FeignClientException.NotFound ex) {
            return new ValidateUserResponse(false, String.format("Author not found: %d", authorId));
        } catch (FeignException e) {
            return new ValidateUserResponse(false, String.format("Unable to verify author at this time: %d", authorId));
        }
    }

    public Post findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id {} not found", id)));

        return post;
    }

    @Transactional
    public void updatePostStatus(Long postId, EPostStatus eventStatus) {
        postRepository.updatePostEvent(postId, eventStatus);
    }
}
