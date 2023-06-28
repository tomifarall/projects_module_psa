package com.pca.projects.projects_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class VersionAlreadyHasProjectException extends RuntimeException {

    public VersionAlreadyHasProjectException(String message) {
        super(message);
    }
}
