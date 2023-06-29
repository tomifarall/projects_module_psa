package com.pca.projects.projects_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidProjectException extends BadRequestException {

    public InvalidProjectException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "invalid_project");
    }
}
