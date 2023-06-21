package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.exception.InvalidTaskException;
import com.pca.projects.projects_module.exception.NoWorkHoursRegistriesException;
import com.pca.projects.projects_module.exception.TaskNotFoundException;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import com.pca.projects.projects_module.repository.ProjectTaskRepository;
import com.pca.projects.projects_module.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectService projectService;
    private final WorkHoursRegisterService workHoursRegisterService;

    @Autowired
    public ProjectTaskService(final ProjectTaskRepository projectTaskRepository, final ProjectService projectService, final WorkHoursRegisterService workHoursRegisterService) {
        this.projectTaskRepository = projectTaskRepository;
        this.projectService = projectService;
        this.workHoursRegisterService = workHoursRegisterService;
    }

    public List<ProjectTask> getProjectTasks() {
        return projectTaskRepository.findAll();
    }

    public ProjectTask findById(Long id) {
        ProjectTask task = projectTaskRepository.findProjectTaskById(id);
        if (Objects.isNull(task)) {
            throw new TaskNotFoundException(String.format("Task %d not found", id));
        }
        return task;
    }

    public void deleteTask(Long id) {
        projectTaskRepository.deleteById(id);
    }

    public void save(ProjectTask task) {
        projectTaskRepository.save(task);
    }

    public TaskDTO createTask(Long projectId, ProjectTask task) {
        Project project = projectService.findById(projectId);
        validateTaskData(task);
        task.setStatus(TaskStatus.PENDING);
        task.setStartDate(new Date()); //ver si inicializar otros datos
        task.setProject(project);
        return projectTaskRepository.save(task).convertToDTO();
    }

    private void validateTaskData(ProjectTask task) {
        boolean isTaskInvalid = StringUtils.isEmpty(task.getTitle())
                || StringUtils.isEmpty(task.getDescription())
                || Objects.isNull(task.getEstimatedTime())
                || Objects.isNull(task.getEmployeeId()); //ver que otros atributos agregar

        if (isTaskInvalid) throw new InvalidTaskException("Task data is invalid.");
    }

    public Collection<ProjectTask> getTasksByProject(Long code) {
        return projectTaskRepository.findProjectTasksByProject(projectService.findById(code));
    }

    public TaskDTO updateTask(TaskDTO task, Long id) {
        ProjectTask taskToUpdate = findById(id);
        if (!StringUtils.isEmpty(task.getStatus())) {
            // TODO: CHECK TRANSITION STATUS
            taskToUpdate.setStatus(TaskStatus.getStatusById(task.getStatus()));
        }
        if (!StringUtils.isEmpty(task.getDescription())) {
            taskToUpdate.setDescription(task.getDescription());
        }
        if (!StringUtils.isEmpty(task.getEstimatedTime())) {
            taskToUpdate.setEstimatedTime(task.getEstimatedTime());
        }
        if (!StringUtils.isEmpty(task.getTitle())) {
            taskToUpdate.setTitle(task.getTitle());
        }
        if (!StringUtils.isEmpty(task.getEmployeeId())) {
            taskToUpdate.setEmployeeId(task.getEmployeeId());
        }
        projectTaskRepository.saveAndFlush(taskToUpdate);
        return taskToUpdate.convertToDTO();
    }

    public WorkHoursRegister loadWorkHours(Long id, WorkHoursRegister workHours) {
        ProjectTask task = findById(id);
        //AGREGAR VALIDACION DE RECURSO CON ENDPOINT DEL EQUIPO DE RECURSOS
        workHours.setTask(task);
        return workHoursRegisterService.save(workHours);
    }

    public Double getDifferenceEstimatedAndRealHoursTask(Long id) {
        ProjectTask task = findById(id);
        if (Objects.isNull(task)) {
            throw new TaskNotFoundException(String.format("Task %d not found", id));
        }
        Collection<WorkHoursRegister> workHours = workHoursRegisterService.getWorkHoursByTask(task);
        if (workHours.isEmpty()) {
            throw new NoWorkHoursRegistriesException(String.format("No hay horas de trabajo cargadas para la tarea, las horas estimadas de la tarea son %d", task.getEstimatedTime()));
        }
        Double workedHours = workHours.stream().map(wh->wh.getAmountHours()).reduce(0d, (a, b) -> a + b);
        return task.getEstimatedTime() - workedHours;
    }
}
