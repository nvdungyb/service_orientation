package com.java.postservice.exception;

public class AuthorValidationException extends RuntimeException {
    public AuthorValidationException(String message) {
        super(message);
    }
}
