package com.java.postservice.utils;

import com.java.postservice.domain.Post;
import com.java.postservice.dto.request.PostCreationDto;
import com.java.postservice.dto.response.PostResponseDto;
import com.java.postservice.enums.EPostStatus;
import com.java.postservice.enums.EVisibility;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post toEntity(PostCreationDto postCreationDto) {
        return Post.builder()
                .title(postCreationDto.getTitle())
                .content(postCreationDto.getContent())
                .authorId(postCreationDto.getAuthorId())
                .status(EPostStatus.PENDING)        //  PENDING, PUBLISHED, APPROVED, REJECTED
                .tags(postCreationDto.getTags())
                .visibility(EVisibility.PUBLIC)
                .attachments(postCreationDto.getAttachments())
                .build();
    }

    public PostResponseDto toResponseDto(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .authorId(post.getAuthorId())
                .status(post.getStatus())
                .tags(post.getTags())
                .attachments(post.getAttachments())
                .visibility(post.getVisibility())
                .build();
    }
}
