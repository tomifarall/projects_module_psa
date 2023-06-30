package com.pca.projects.projects_module.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseAPIException {
    public BadRequestException(String message, HttpStatus status, String error) {
        super(message, status, error);
    }
}
