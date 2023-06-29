package com.pca.projects.projects_module.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseAPIException extends RuntimeException {
    private final HttpStatus status;
    private final String error;

    public BaseAPIException(String message, HttpStatus status, String error) {
        super(message);
        this.status = status;
        this.error = error;
    }
}
