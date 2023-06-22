package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<ProjectDTO> getProjects() {
        List<Project> projects = projectService.getProjects();
        return projects.stream()
                .map(Project::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/projects/{id}")
    public ProjectDTO getProject(@PathVariable Long id) {
        Project project = projectService.findById(id);
        return project.convertToDTO();
    }

    @ResponseStatus(CREATED)
    @PostMapping("/projects")
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        Project projectToCreate = projectDTO.convertToEntity();
        return projectService.createToDTO(projectToCreate);
    }

    @PutMapping("/projects/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        return projectService.updateProject(id, projectDTO);
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
