package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;

public class ProjectTaskController {

    private final ProjectTaskService projectTaskService;

    @Autowired
    public ProjectTaskController(final ProjectTaskService projectTaskService) { this.projectTaskService = projectTaskService; }

    @GetMapping("/projectsTask")
    public Collection<ProjectTask> getProjects() {
        return projectTaskService.getProjectTasks();
    }

    @GetMapping("/projectsTask/{id}")
    public ResponseEntity<ProjectTask> getProject(@PathVariable Long id) {
        Optional<ProjectTask> projectOptional = projectTaskService.findById(id);
        return ResponseEntity.of(projectOptional);
    }
}
