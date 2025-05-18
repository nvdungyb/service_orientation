package com.java.userservice.utils;

import com.java.userservice.domain.Role;
import com.java.userservice.domain.User;
import com.java.userservice.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserMapper {
    public UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .eRoles(user.getRoles().stream()
                        .map(role -> role.getErole())
                        .toList())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User toEntity(String username, String email, List<Role> roles, String password, boolean status) {
        return User.builder()
                .username(username)
                .email(email)
                .roles(roles)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .password(password)
                .build();
    }
}
