package com.java.postservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PostCreationDto {
    @Size(min = 1, max = 500)
    @NotBlank(message = "Title length is at least 1 character and at most 500 characters")
    private String title;

    @NotBlank(message = "Content length is at least 1 character and at most 10_000 characters")
    @Size(min = 1, max = 10_000)
    private String content;

    @NotNull(message = "Author ID cannot be empty")
    private Long authorId;
    private List<String> tags;
    private List<String> attachments;
}
