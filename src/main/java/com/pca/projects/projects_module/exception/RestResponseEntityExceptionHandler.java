package com.pca.projects.projects_module.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BaseAPIException.class})
    public ResponseEntity<Object> handleBaseAPIException(BaseAPIException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), ex.getError(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
