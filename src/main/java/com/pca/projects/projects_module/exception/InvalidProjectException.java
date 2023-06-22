package com.pca.projects.projects_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)

public class InvalidProjectException extends RuntimeException {

    public InvalidProjectException(String message) {
        super(message);
    }
}
