package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;

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
    public ProjectDTO getProject(@PathVariable Long code) {
        /* Internally, We are going to use "id" instead of "code" */
        Project project = projectService.findById(code);
        return project.convertToDTO();
    }

    @ResponseStatus(CREATED)
    @PostMapping("/projects")
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        Project projectToCreate = projectDTO.convertToEntity();
        return projectService.createToDTO(projectToCreate);
    }

    @PutMapping("/projects/{code}")
    public ProjectDTO updateProject(@PathVariable Long code, @RequestBody ProjectDTO projectDTO) {
        return projectService.updateProject(code, projectDTO);
    }

    @DeleteMapping("/projects/{code}")
    public void deleteProject(@PathVariable Long code) {
        projectService.deleteProject(code);
    }
}
