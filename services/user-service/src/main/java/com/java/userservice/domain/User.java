package com.java.userservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
@Getter
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @Size(min = 3, max = 50)
    private String username;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @OneToMany
    @JoinColumn(name = "role_id")
    private List<Role> roles;

    @Column(nullable = false, length = 50)
    @Size(min = 1, max = 50)
    private String password;
    private Boolean isActive;
    private LocalDateTime createdAt;

    @Version
    private Long version;

    public User() {
    }
}
