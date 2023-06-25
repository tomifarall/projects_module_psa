package com.pca.projects.projects_module.controller.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeDTO {
    private Long id;

    private String name;

    private String lastName;
}
