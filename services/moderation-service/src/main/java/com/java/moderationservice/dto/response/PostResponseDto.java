package com.java.moderationservice.dto.response;

import com.java.moderationservice.enums.EPostStatus;
import com.java.moderationservice.enums.EVisibility;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostResponseDto {
    private String title;
    private String content;
    private Long authorId;
    private List<String> tags;
    private List<String> attachments;
    private EPostStatus status;
    private EVisibility visibility;
}
