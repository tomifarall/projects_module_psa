package com.pca.projects.projects_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends BadRequestException {

    public ProjectNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "project_not_found");
    }
}
