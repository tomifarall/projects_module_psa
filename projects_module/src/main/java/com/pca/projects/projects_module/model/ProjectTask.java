package com.pca.projects.projects_module.model;

import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.utils.TaskStatus;
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
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer type_id;

    private Integer priority;

    private TaskStatus status;

    private Date startDate;

    private Date endDate;

    private Double estimatedTime;

    private String employeeId;

    @ManyToOne(fetch=FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy="task")
    private List<WorkHoursRegister> timeWorked;

    public TaskDTO convertToDTO() {
        return TaskDTO.builder()
                .id(id)
                .title(title)
                .description(description)
                .type_id(type_id)
                .priority(priority)
                .status(status.getId())
                .startDate(startDate)
                .endDate(endDate)
                .estimatedTime(estimatedTime)
                .employeeId(employeeId)
                .project(project)
                .timeWorked(timeWorked)
                .build();
    }
}
