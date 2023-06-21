package com.pca.projects.projects_module.model;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.utils.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Column(name = "tasks_quantity")
    private Integer tasksQuantity;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProjectTask> projectTasks;

    public ProjectDTO convertToDTO() {
        List<TaskDTO> tasks = new ArrayList<>();
        if (!CollectionUtils.isEmpty(projectTasks)) {
            tasks.addAll(projectTasks.stream()
                    .map(ProjectTask::convertToDTO)
                    .collect(Collectors.toList()));
        }

        return ProjectDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .startDate(startDate)
                .status(status.getId())
                .endDate(endDate)
                .hoursWorked(hoursWorked)
                .tasksQuantity(tasksQuantity)
                .tasks(tasks)
                .build();
    }
}
