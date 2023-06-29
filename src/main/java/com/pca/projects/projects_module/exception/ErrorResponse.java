package com.pca.projects.projects_module.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class ErrorResponse implements Serializable {

    private HttpStatus status;
    private String message;
    private String error;
    private int statusCode;

    public ErrorResponse(HttpStatus status, String error, String message) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.statusCode = status.value();

    }
}
