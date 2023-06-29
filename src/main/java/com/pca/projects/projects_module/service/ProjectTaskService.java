package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.exception.*;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.repository.ProjectTaskRepository;
import com.pca.projects.projects_module.service.client.DTO.HoursRegisterDTO;
import com.pca.projects.projects_module.service.client.DTO.ResourceDTO;
import com.pca.projects.projects_module.service.client.ResourcesClientService;
import com.pca.projects.projects_module.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pca.projects.projects_module.utils.ProjectTaskUtils.formatProjectTask;
import static com.pca.projects.projects_module.utils.ProjectTaskUtils.formatProjectTasks;

@Service
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectService projectService;
    private final ResourcesClientService resourcesClientService;


    @Autowired
    public ProjectTaskService(final ProjectTaskRepository projectTaskRepository,
                              final ProjectService projectService,
                              final ResourcesClientService resourcesClientService) {
        this.projectTaskRepository = projectTaskRepository;
        this.projectService = projectService;
        this.resourcesClientService = resourcesClientService;
    }

    public List<ProjectTask> findAllProjectTasks() {
        return projectTaskRepository.findAll();
    }

    public ProjectTask findById(Long id) {
        ProjectTask task = projectTaskRepository.findProjectTaskById(id);
        if (Objects.isNull(task)) {
            throw new TaskNotFoundException(String.format("Task %d not found", id));
        }
        return task;
    }

    public ResourceDTO getTaskEmployeeInfo(Long employeeId) {
        return resourcesClientService.getResource(employeeId);
    }

    public Double getTaskWorkedHours(ProjectTask task) {
        List<HoursRegisterDTO> taskHoursRegisters = resourcesClientService.getResourceRegistersByTask(task.getEmployeeId(),
                task.getProject().getId(), task.getId());

        return taskHoursRegisters.stream()
                .map(HoursRegisterDTO::getHours)
                .reduce(0D, Double::sum);
    }

    public List<TaskDTO> getProjectsTasks() {
        List<ProjectTask> tasks = findAllProjectTasks();
        return formatProjectTasks(tasks);
    }

    public TaskDTO getProjectTask(Long id) {
        ProjectTask task = findById(id);
        return formatProjectTask(task, this);
    }

    public void deleteTask(Long id) {
        projectTaskRepository.deleteById(id);
    }


    private ResourceDTO validateEmployee(Long employeeId) {
        try {
            return resourcesClientService.getResource(employeeId);
        } catch (NotFoundException notFoundException) {
            throw new InvalidEmployeeException("Employee does not exist.");
        }
    }

    private void validateTaskData(ProjectTask task) {
        boolean isTaskInvalid = StringUtils.isEmpty(task.getTitle())
                || StringUtils.isEmpty(task.getDescription())
                || Objects.isNull(task.getEstimatedTime())
                || Objects.isNull(task.getEmployeeId())
                || Objects.isNull(task.getTaskType())
                || Objects.isNull(task.getTaskPriority());

        if (isTaskInvalid) {
            throw new InvalidTaskException("Task data is invalid. " +
                    "Title, description, estimatedTime, employeeId, type or priority is missing.");
        }
    }

    @Transactional
    public TaskDTO createTask(Long projectId, ProjectTask task) {
        Project project = projectService.findById(projectId);
        validateTaskData(task);
        validateEmployee(task.getEmployeeId());
        task.setStatus(TaskStatus.PENDING);
        task.setProject(project);
        ProjectTask taskCreated = projectTaskRepository.save(task);
        return formatProjectTask(taskCreated, this);
    }

    public List<ProjectTask> findTasksByProject(Long id) {
        return projectTaskRepository.findProjectTasksByProject(projectService.findById(id));
    }

    public List<TaskDTO> getTasksByProject(Long projectId) {
        List<ProjectTask> projectTasks = findTasksByProject(projectId);
        return formatProjectTasks(projectTasks);
    }

    public TaskDTO updateTask(TaskDTO task, Long id) {
        ProjectTask taskToUpdate = findById(id);
        if (!StringUtils.isEmpty(task.getStatus())) {
            TaskStatus newTaskStatus = TaskStatus.getStatusById(task.getStatus());
            checkAndSetTaskStatus(taskToUpdate, newTaskStatus);
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
            validateEmployee(task.getEmployeeId());
            taskToUpdate.setEmployeeId(task.getEmployeeId());
        }
        projectTaskRepository.saveAndFlush(taskToUpdate);

        return formatProjectTask(taskToUpdate, this);
    }

    private void checkAndSetTaskStatus(ProjectTask task, TaskStatus taskStatus) {
        task.getStatus().checkTransitionStatus(taskStatus);
        if(!TaskStatus.PENDING.equals(taskStatus) && Objects.isNull(task.getStartDate())) {
            task.setStartDate(new Date());
        }
        if (TaskStatus.FINISHED.equals(taskStatus)) {
            task.setEndDate(new Date());
        }
        task.setStatus(taskStatus);
    }

    public List<TaskDTO> search(String title) {
        List<ProjectTask> projectTasks = projectTaskRepository.findProjectTasksByTitleContainingIgnoreCase(title);
        return projectTasks.stream()
                .map(ProjectTask::convertToDTO)
                .collect(Collectors.toList());
    }
}
