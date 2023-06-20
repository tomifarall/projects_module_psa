package com.pca.projects.projects_module.model;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.utils.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private ProjectStatus status;

    private Date startDate;

    private Date endDate;

    private Double hoursWorked;

    private Integer tasksQuantity;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProjectTask> projectTasks;

    public ProjectDTO convertToDTO() {
        return ProjectDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .hoursWorked(hoursWorked)
                .tasksQuantity(tasksQuantity)
                .tasks(projectTasks)
                .build();
    }
}
