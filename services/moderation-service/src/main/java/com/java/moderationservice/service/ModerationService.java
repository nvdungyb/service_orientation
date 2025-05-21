package com.java.moderationservice.service;

import com.java.moderationservice.domain.ModerateOutbox;
import com.java.moderationservice.dto.response.PostResponseDto;
import com.java.moderationservice.enums.EPostStatus;
import com.java.moderationservice.enums.EVisibility;
import com.java.moderationservice.exception.ModeratePostException;
import com.java.moderationservice.http.PostServiceClient;
import com.java.moderationservice.messaging.event.PostCreatedEvent;
import com.java.moderationservice.messaging.event.PostOutbox;
import com.java.moderationservice.repository.ModerateOutboxRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class ModerationService {

    private final PostServiceClient postServiceClient;
    private final Executor taskExecutor;
    private final ForbiddenWordCheckerService forbiddenWordCheckerService;
    private final ModerateOutboxRepository moderateOutboxRepository;

    public ModerationService(PostServiceClient postServiceClient, @Qualifier("taskExecutor") Executor taskExecutor, ForbiddenWordCheckerService forbiddenWordCheckerService, ModerateOutboxRepository approvedOutboxRepository) {
        this.postServiceClient = postServiceClient;
        this.taskExecutor = taskExecutor;
        this.forbiddenWordCheckerService = forbiddenWordCheckerService;
        this.moderateOutboxRepository = approvedOutboxRepository;
    }

    public boolean moderatePost(PostCreatedEvent event) {
        Long postId = event.postId();
        Long authorId = event.authorId();

        log.info("Moderating post with id " + postId + " and author " + authorId);

        // Call Post API, maybe call fail
        PostResponseDto postResponse = postServiceClient.getPostById(postId)
                .orElseThrow(() -> new ModeratePostException(String.format("Post with id {} can not be moderate at this time", postId)));

        // Chỉ moderate post ở trạng thái pending, visibility: public
        if (postResponse.getStatus() != EPostStatus.PENDING || postResponse.getVisibility() == EVisibility.PRIVATE) {
            return false;
        }

        boolean isPostValid = isPostTitleAndContentValid(postResponse);
        if (!isPostValid) {
            return false;
        }

        // todo: moderate post's attachment: eg image text

        return true;
    }

    private boolean isPostTitleAndContentValid(PostResponseDto postResponse) {
        CompletableFuture<Boolean> titleFuture = CompletableFuture.supplyAsync(() -> moderatePostTitle(postResponse.getTitle()), taskExecutor);
        CompletableFuture<Boolean> contentFuture = CompletableFuture.supplyAsync(() -> moderatePostContent(postResponse.getContent()), taskExecutor);
        return titleFuture.thenCombine(contentFuture,
                        (isTitleValid, isContentValid) -> (isTitleValid && isContentValid))
                .join();
    }

    private boolean moderatePostContent(String content) {
        return forbiddenWordCheckerService.check(content).isEmpty();
    }

    private boolean moderatePostTitle(String title) {
        return forbiddenWordCheckerService.check(title).isEmpty();
    }

    @Transactional
    public void saveModerateOutbox(PostOutbox event, EPostStatus status) {
        ModerateOutbox outbox = ModerateOutbox.builder()
                .event_id(event.getEvent_id())
                .payload(event.getPayload())
                .created_at(LocalDateTime.now())
                .event_type(status)
                .aggregate_id(event.getAggregate_id())
                .build();

        moderateOutboxRepository.save(outbox);
    }
}
