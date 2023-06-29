package com.pca.projects.projects_module.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(code = HttpStatus.BAD_REQUEST)

@Getter
public class VersionAlreadyHasProjectException extends BadRequestException {

    public VersionAlreadyHasProjectException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "version_already_has_a_project");
    }
}
