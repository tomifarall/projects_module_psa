package com.pca.projects.projects_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotFoundException extends BaseAPIException {
    public NotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
