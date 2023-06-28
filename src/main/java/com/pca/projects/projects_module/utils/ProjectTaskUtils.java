package com.pca.projects.projects_module.utils;

import com.pca.projects.projects_module.controller.DTO.EmployeeDTO;
import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.service.ProjectTaskService;
import com.pca.projects.projects_module.service.client.DTO.ResourceDTO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectTaskUtils {

    public static TaskDTO formatProjectTask(ProjectTask projectTask,
                                            ProjectTaskService projectTaskService) {
        ResourceDTO resource = projectTaskService.getTaskEmployeeInfo(projectTask.getEmployeeId());
        Double timeWorked = projectTaskService.getTaskWorkedHours(projectTask);


        TaskDTO taskDTO = projectTask.convertToDTO();
        taskDTO.setEmployeeInfo(EmployeeDTO.builder()
                .id(resource.getId())
                .name(resource.getName())
                .lastName(resource.getLastname())
                .build());
        taskDTO.setTimeWorked(timeWorked);

        return taskDTO;
    }


    public static List<TaskDTO> formatProjectTasks (List<ProjectTask> projectTasks,
                                                    ProjectTaskService projectTaskService) {
        if (Objects.isNull(projectTasks)) return Collections.emptyList();
        return projectTasks.stream()
                .parallel()
                .map(task -> formatProjectTask(task, projectTaskService))
                .collect(Collectors.toList());
    }
}
