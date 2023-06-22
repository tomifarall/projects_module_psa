package com.pca.projects.projects_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class NoWorkHoursRegistriesException extends RuntimeException {

    public NoWorkHoursRegistriesException(String message) {
        super(message);
    }
}
