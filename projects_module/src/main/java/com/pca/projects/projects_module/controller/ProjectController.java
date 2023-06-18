package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController

public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public Collection<Project> getProjects() {
        return projectService.getProjects();
    }

    @GetMapping("/projects/{code}")
    public ResponseEntity<Project> getProject(@PathVariable Long code) {
        /* Internally, We are going to use "id" instead of "code" */
        Optional<Project> projectOptional = projectService.findById(code);
        return ResponseEntity.of(projectOptional);
    }
}
