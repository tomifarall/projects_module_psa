package com.pca.projects.projects_module.controller.DTO;

import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import com.pca.projects.projects_module.utils.TaskPriority;
import com.pca.projects.projects_module.utils.TaskStatus;
import com.pca.projects.projects_module.utils.TaskType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class TaskDTO {

    private Long id;

    private String title;

    private String description;

    private String taskType;

    private String taskPriority;

    private String status;

    private Date startDate;

    private Date endDate;

    private Double estimatedTime;

    private Long employeeId;

    private ProjectDTO project;

    private List<WorkHoursRegister> timeWorked;

    public ProjectTask convertToEntity() {
        return ProjectTask.builder()
                .id(id)
                .title(title)
                .description(description)
                .taskType(TaskType.getTaskTypeById(taskType))
                .taskPriority(TaskPriority.getTaskTypeById(taskPriority))
                .status(TaskStatus.getStatusById(status))
                .startDate(startDate)
                .endDate(endDate)
                .estimatedTime(estimatedTime)
                .employeeId(employeeId)
                .timeWorked(timeWorked)
                .build();
    }
}
