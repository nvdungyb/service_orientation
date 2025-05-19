package com.java.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<V> {
    int status;
    String message;
    V data;
    LocalDateTime time;
    String path;
}
