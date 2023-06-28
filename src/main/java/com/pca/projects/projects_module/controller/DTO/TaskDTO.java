package com.pca.projects.projects_module.controller.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.utils.TaskPriority;
import com.pca.projects.projects_module.utils.TaskStatus;
import com.pca.projects.projects_module.utils.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private EmployeeDTO employeeInfo;

    private ProjectDTO project;

    private Double timeWorked;

    public ProjectTask convertToEntity() {
        TaskType type = Objects.nonNull(taskType)
                ? TaskType.getTaskTypeById(taskType)
                : null;

        TaskPriority priority = Objects.nonNull(taskPriority)
                ? TaskPriority.getTaskTypeById(taskPriority)
                : null;

        TaskStatus taskStatus = Objects.nonNull(status)
                ? TaskStatus.getStatusById(status)
                : null;

        return ProjectTask.builder()
                .id(id)
                .title(title)
                .description(description)
                .taskType(type)
                .taskPriority(priority)
                .status(taskStatus)
                .startDate(startDate)
                .endDate(endDate)
                .estimatedTime(estimatedTime)
                .employeeId(employeeId)
                .build();
    }
}
