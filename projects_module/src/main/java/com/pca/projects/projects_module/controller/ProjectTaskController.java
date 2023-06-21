package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import com.pca.projects.projects_module.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ProjectTaskController {

    private final ProjectTaskService projectTaskService;

    @Autowired
    public ProjectTaskController(final ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @GetMapping("/projectsTask")
    public Collection<ProjectTask> getProjectTask() {
        return projectTaskService.getProjectTasks();
    }

    @GetMapping("/projects/{id}/tasks")
    public Collection<ProjectTask> getTasksByProjectId(@PathVariable Long id) {
        return projectTaskService.getTasksByProject(id);
    }

    @GetMapping("/projectsTask/{id}")
    public TaskDTO getProjectTaskById(@PathVariable Long id) {
        return projectTaskService.findById(id).convertToDTO();
    }

    @PostMapping("/projects/{id}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@PathVariable Long id, @RequestBody TaskDTO task) {
        return projectTaskService.createTask(id, task.convertToEntity());
    }

    @DeleteMapping("/projectsTask/{id}")
    public void deleteTask(@PathVariable Long id) {
        projectTaskService.deleteTask(id);
    }

    @PutMapping("/projectsTask/{id}")
    public TaskDTO updateTask(@RequestBody TaskDTO task, @PathVariable Long id) {
        return projectTaskService.updateTask(task, id);
    }

    @PutMapping("/projectsTask/{id}/loadWorkHours")
    public WorkHoursRegister loadWorkHours(@PathVariable Long id, WorkHoursRegister workHours) {
        return projectTaskService.loadWorkHours(id, workHours);
    }

    @GetMapping("/projectsTask/{id}/estimatedDifference")
    public Double getDifferenceEstimatedAndRealHoursTask(@PathVariable Long id) {
        return projectTaskService.getDifferenceEstimatedAndRealHoursTask(id);
    }

}
