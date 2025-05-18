package com.java.userservice.exception;

import com.java.userservice.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handlerEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .data(ex.getMessage())
                .time(LocalDateTime.now())
                .build(),
                HttpStatus.NOT_FOUND);
    }
}
