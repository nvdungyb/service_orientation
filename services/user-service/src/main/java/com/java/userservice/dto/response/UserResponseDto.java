package com.java.userservice.dto.response;

import com.java.userservice.enums.Erole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserResponseDto {
    private String username;
    private String email;
    private List<Erole> eRoles;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
