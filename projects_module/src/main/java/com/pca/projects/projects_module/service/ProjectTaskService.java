package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import com.pca.projects.projects_module.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public Optional<ProjectTask> findById(Long id) {
        return projectTaskRepository.findById(id);
    }

    public void deleteTask(Long id) {
        projectTaskRepository.deleteById(id);
    }

    public void save(ProjectTask task) {
        projectTaskRepository.save(task);
    }

    public ProjectTask createTask(ProjectTask task) {
        return projectTaskRepository.save(task);
    }

    public Collection<ProjectTask> getTasksByProject(Long code) {
        return projectTaskRepository.findProjectTasksByProject(projectService.findById(code));
    }

    public ResponseEntity<ProjectTask> updateTask(ProjectTask task, Long id) {
        Optional<ProjectTask> optionalTask = findById(id);
        if (!optionalTask.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        task.setId(id);
        save(task);
        return ResponseEntity.ok().build();
    }

    public WorkHoursRegister loadWorkHours(Long id, WorkHoursRegister workHours) {
        if (workHours.getAmountHours() > 24) {
            return null; //EXCEPCION DE MUCHAS HORAS CARGADAS
        }
        Optional<ProjectTask> optionalTask = findById(id);
        if (!optionalTask.isPresent()) {
            return null; //agregar excepción
        }
        //AGREGAR VALIDACION DE RECURSO CON ENDPOINT DEL EQUIPO DE RECURSOS
        workHours.setTask(optionalTask.get());
        return workHoursRegisterService.save(workHours);
    }

    public Double getDifferenceEstimatedAndRealHoursTask(Long id) {
        Optional<ProjectTask> optionalTask = findById(id);
        if (!optionalTask.isPresent()) {
            return null; //EXCEPCION NO EXISTE TASK
        }
        ProjectTask task = optionalTask.get();
        Collection<WorkHoursRegister> workHours = workHoursRegisterService.getWorkHoursByTask(task);
        if (workHours.isEmpty()) {
            return null; //Crear excepción de que no hay horas cargadas
        }
        Double workedHours = workHours.stream().map(wh->wh.getAmountHours()).reduce(0d, (a, b) -> a + b);
        return task.getEstimatedTime() - workedHours;
    }
}
