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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private ProjectStatus status;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "hours_worked")
    private Double hoursWorked;

    @Column(name = "task_quantity")
    private Integer tasksQuantity;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProjectTask> projectTasks;

    public ProjectDTO convertToDTO() {
        return ProjectDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .startDate(startDate)
                .status(status.getId())
                .endDate(endDate)
                .hoursWorked(hoursWorked)
                .tasksQuantity(tasksQuantity)
                .tasks(projectTasks)
                .build();
    }
}
