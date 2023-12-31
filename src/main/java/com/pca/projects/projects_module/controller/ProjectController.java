package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Api(tags = "Project Controller")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @ApiOperation("Obtener todos los proyectos")
    @GetMapping("/projects")
    public List<ProjectDTO> getProjects() {
        return projectService.getAllProjects();
    }

    @ApiOperation("Obtener un proyecto por su ID")
    @GetMapping("/projects/{id}")
    public ProjectDTO getProject(@ApiParam(value = "ID del proyecto", example = "1") @PathVariable Long id) {
        return projectService.getProject(id);
    }

    @ApiOperation("Buscar proyectos por nombre")
    @GetMapping("/projects/search")
    public List<ProjectDTO> searchProjects(@ApiParam(value = "Nombre del proyecto") @RequestParam String name) {
        return projectService.search(name);
    }

    @ApiOperation("Crear un nuevo proyecto")
    @ResponseStatus(CREATED)
    @PostMapping("/projects")
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        Project projectToCreate = projectDTO.convertToEntity();
        return projectService.createToDTO(projectToCreate);
    }

    @ApiOperation("Actualizar un proyecto existente")
    @PutMapping("/projects/{id}")
    public ProjectDTO updateProject(@ApiParam(value = "ID del proyecto", example = "1") @PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        return projectService.updateProject(id, projectDTO);
    }

    @ApiOperation("Eliminar un proyecto")
    @DeleteMapping("/projects/{id}")
    public void deleteProject(@ApiParam(value = "ID del proyecto", example = "1") @PathVariable Long id) {
        projectService.deleteProject(id);
    }
}
