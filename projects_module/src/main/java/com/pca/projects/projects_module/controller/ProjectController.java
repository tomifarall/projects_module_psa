package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }
}
