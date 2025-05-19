package com.java.postservice.domain;

import com.java.postservice.enums.EPostStatus;
import com.java.postservice.enums.EVisibility;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    @Size(min = 1, max = 500)
    private String title;

    @Column(nullable = false, length = 10_000)
    @Size(min = 1, max = 10_000)
    private String content;

    @Column(nullable = false, name = "author_id")
    @NotNull
    private Long authorId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EPostStatus status;

    @Column(nullable = false, name = "create_at")
    private LocalDateTime createAt;

    @Column(nullable = false, name = "update_at")
    private LocalDateTime updateAt;

    @ElementCollection
    @CollectionTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tags")
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EVisibility visibility;

    @ElementCollection
    @CollectionTable(name = "post_attachments", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "attachment")
    private List<String> attachments;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }
}
