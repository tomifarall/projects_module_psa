package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import com.pca.projects.projects_module.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class ProjectTaskController {

    private final ProjectTaskService projectTaskService;

    @Autowired
    public ProjectTaskController(final ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @GetMapping("/project/{code}/task")
    public Collection<ProjectTask> getTasksByProjectCode(@PathVariable Long code) {
        return projectTaskService.getTasksByProject(code);
    }

    @GetMapping("/projectsTask/{id}")
    public ResponseEntity<ProjectTask> getProjectTaskById(@PathVariable Long id) {
        Optional<ProjectTask> projectOptional = projectTaskService.findById(id);
        return ResponseEntity.of(projectOptional);
    }

    @PostMapping("/projectsTask")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectTask createTask(@RequestBody ProjectTask task) {
        return projectTaskService.createTask(task);
    }

    @DeleteMapping("/projectsTask/{id}")
    public void deleteTask(@PathVariable Long id) {
        projectTaskService.deleteTask(id);
    }

    @PutMapping("/projectsTask/{id}")
    public ResponseEntity<ProjectTask> updateTask(@RequestBody ProjectTask task, @PathVariable Long id) {
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
