package com.pca.projects.projects_module.controller.DTO;

import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ProjectDTO {

    private Long id;

    private String name;

    private String description;

    private String status;

    private Date startDate;

    private Date endDate;

    private Double hoursWorked;

    private Integer tasksQuantity;

    private List<TaskDTO> tasks;

    public Project convertToEntity() {
        return Project.builder()
                .name(name)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
