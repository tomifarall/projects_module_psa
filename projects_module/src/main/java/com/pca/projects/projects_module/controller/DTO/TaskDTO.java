package com.pca.projects.projects_module.controller.DTO;

import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import com.pca.projects.projects_module.utils.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private Integer type_id;
    private Integer priority;
    private String status;
    private Date startDate;
    private Date endDate;
    private Double estimatedTime;
    private String employeeId;
    private Project project;
    private List<WorkHoursRegister> timeWorked;

    public ProjectTask convertToEntity() {
        return ProjectTask.builder()
                .id(id)
                .title(title)
                .description(description)
                .type_id(type_id)
                .priority(priority)
                .status(TaskStatus.getStatusById(status))
                .startDate(startDate)
                .endDate(endDate)
                .estimatedTime(estimatedTime)
                .employeeId(employeeId)
                .project(project)
                .timeWorked(timeWorked)
                .build();
    }
}
