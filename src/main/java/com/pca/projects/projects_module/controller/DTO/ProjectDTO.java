package com.pca.projects.projects_module.controller.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pca.projects.projects_module.model.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Long id;

    private String name;

    private String description;

    private String status;

    private Date startDate;

    private Date endDate;

    private Double hoursWorked;

    private Integer tasksQuantity;

    private Long versionId;

    private String versionCode;

    private List<TaskDTO> tasks;

    public Project convertToEntity() {
        return Project.builder()
                .name(name)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .versionId(versionId)
                .build();
    }
}
