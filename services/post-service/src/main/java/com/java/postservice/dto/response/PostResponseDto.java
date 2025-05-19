package com.java.postservice.dto.response;

import com.java.postservice.enums.EPostStatus;
import com.java.postservice.enums.EVisibility;
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
