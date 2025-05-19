package com.java.postservice.exception;

import com.java.postservice.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlerMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .message(errorMessage)
                .time(LocalDateTime.now())
                .path(request.getRequestURI())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorValidationException.class)
    public ResponseEntity<ApiResponse<?>> handlerAuthorValidation(AuthorValidationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .path(request.getRequestURI())
                .data(null)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostCreationException.class)
    public ResponseEntity<ApiResponse<?>> handlerAuthorValidation(PostCreationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .path(request.getRequestURI())
                .data(null)
                .build(), HttpStatus.BAD_REQUEST);
    }

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
