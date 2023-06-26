package com.pca.projects.projects_module.controller;

import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import com.pca.projects.projects_module.service.ProjectTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Project Task Controller")
public class ProjectTaskController {

    private final ProjectTaskService projectTaskService;

    @Autowired
    public ProjectTaskController(final ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @ApiOperation("Obtener todas las tareas de todos los proyectos")
    @GetMapping("/projects/tasks")
    public List<TaskDTO> getProjectTask() {
        return projectTaskService.getProjectTasks().stream()
                .map(ProjectTask::convertToDTO)
                .collect(Collectors.toList());
    }

    @ApiOperation("Obtener tareas por ID de proyecto")
    @GetMapping("/projects/{id}/tasks")
    public List<TaskDTO> getTasksByProjectId(@ApiParam(value = "ID del proyecto", example = "1") @PathVariable Long id) {
        return projectTaskService.getTasksByProject(id).stream()
                .map(ProjectTask::convertToDTO)
                .collect(Collectors.toList());
    }

    @ApiOperation("Obtener una tarea por su ID")
    @GetMapping("/projects/tasks/{id}")
    public TaskDTO getProjectTaskById(@ApiParam(value = "ID de la tarea", example = "1") @PathVariable Long id) {
        return projectTaskService.findById(id).convertToDTO();
    }

    @ApiOperation("Crear una tarea en un proyecto")
    @PostMapping("/projects/{id}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@ApiParam(value = "ID del proyecto", example = "1") @PathVariable Long id, @RequestBody TaskDTO task) {
        return projectTaskService.createTask(id, task.convertToEntity());
    }

    @ApiOperation("Eliminar una tarea")
    @DeleteMapping("/projects/tasks/{id}")
    public void deleteTask(@ApiParam(value = "ID de la tarea", example = "1") @PathVariable Long id) {
        projectTaskService.deleteTask(id);
    }

    @ApiOperation("Actualizar una tarea")
    @PutMapping("/projects/tasks/{id}")
    public TaskDTO updateTask(@RequestBody TaskDTO task, @ApiParam(value = "ID de la tarea", example = "1") @PathVariable Long id) {
        return projectTaskService.updateTask(task, id);
    }

    @ApiOperation("Cargar horas de trabajo en una tarea")
    @PutMapping("/projects/tasks/{id}/loadWorkHours")
    public WorkHoursRegister loadWorkHours(@ApiParam(value = "ID de la tarea", example = "1") @PathVariable Long id, @RequestBody WorkHoursRegister workHours) {
        return projectTaskService.loadWorkHours(id, workHours);
    }

    @ApiOperation("Obtener la diferencia entre las horas estimadas y reales de una tarea")
    @GetMapping("/projects/tasks/{id}/estimatedDifference")
    public Double getDifferenceEstimatedAndRealHoursTask(@ApiParam(value = "ID de la tarea", example = "1") @PathVariable Long id) {
        return projectTaskService.getDifferenceEstimatedAndRealHoursTask(id);
    }
}
