package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.exception.InvalidProjectException;
import com.pca.projects.projects_module.exception.ProjectNotFoundException;
import com.pca.projects.projects_module.exception.VersionAlreadyHasProjectException;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.repository.ProjectRepository;
import com.pca.projects.projects_module.service.client.DTO.HoursRegisterDTO;
import com.pca.projects.projects_module.service.client.DTO.VersionDTO;
import com.pca.projects.projects_module.service.client.ResourcesClientService;
import com.pca.projects.projects_module.service.client.SupportClientService;
import com.pca.projects.projects_module.utils.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pca.projects.projects_module.utils.ProjectTaskUtils.formatProjectTasks;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectTaskService projectTaskService;

    private final SupportClientService supportClientService;

    private final ResourcesClientService resourcesClientService;

    @Autowired
    public ProjectService(final ProjectRepository projectRepository, final ProjectTaskService projectTaskService,
                          final SupportClientService supportClientService, final ResourcesClientService resourcesClientService) {
        this.projectRepository = projectRepository;
        this.projectTaskService = projectTaskService;
        this.supportClientService = supportClientService;
        this.resourcesClientService = resourcesClientService;
    }


    @Transactional
    public ProjectDTO createToDTO(Project project) {
        Project createdProject = create(project);
        return formatProject(createdProject);
    }

    private void validateProjectData(Project project) {
        boolean isProjectInvalid = StringUtils.isEmpty(project.getName())
                || StringUtils.isEmpty(project.getDescription())
                || Objects.isNull(project.getStartDate())
                || Objects.isNull(project.getEndDate())
                || Objects.isNull(project.getVersionId())
                || Objects.isNull(project.getResponsibleId());

        if (isProjectInvalid) throw new InvalidProjectException("Project data is invalid.");
    }

    private void validateIfVersionAlreadyBindToProject(Long versionId) {
        Project versionProject = projectRepository.findByVersionId(versionId);
        if (Objects.nonNull(versionProject)) {
            throw new VersionAlreadyHasProjectException(String.format("Version: %s already has a project associated",
                    versionId));
        }
    }

    private void updateVersionProject(Project project) {
        VersionDTO body = VersionDTO.builder()
                .projectId(project.getId())
                .build();
        supportClientService.updateVersion(project.getVersionId(), body);
    }

    @Transactional
    public Project create(Project project) {
        validateProjectData(project);
        validateIfVersionAlreadyBindToProject(project.getVersionId());
        project.setStatus(ProjectStatus.STARTING);
        project.setHoursWorked(0D);
        project.setTasksQuantity(0);
        Project createdProject = projectRepository.save(project);
        updateVersionProject(createdProject);
        return createdProject;
    }

    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(Project::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProjectDTO getProject(Long id) {
        Project project = findById(id);
        return formatProject(project);
    }


    private ProjectDTO formatProject(Project project) {
        ProjectDTO formattedProject = project.convertToDTO();
        //List<TaskDTO> projectTasks = formatProjectTasks(project.getProjectTasks(), projectTaskService);
        VersionDTO versionAssociatedToProject = supportClientService.getVersion(project.getVersionId());
        //formattedProject.setTasks(projectTasks);
        formattedProject.setTasksQuantity(formattedProject.getTasks().size());
        formattedProject.setHoursWorked(getProjectTotalHoursWorked(project));
        formattedProject.setVersionCode(versionAssociatedToProject.getVersionCode());
        return formattedProject;
    }

    private Double getProjectTotalHoursWorked(Project project) {
        List<HoursRegisterDTO> projectHoursRegisters = resourcesClientService.getResourceRegistersByProject(project.getId());
        return projectHoursRegisters.stream()
                .map(HoursRegisterDTO::getHours)
                .reduce(0D, Double::sum);
    }

    public Project findById(Long id) {
        Project project =  projectRepository.findProjectById(id);
        if (Objects.isNull(project)) {
            throw new ProjectNotFoundException(String.format("Project %d not found", id));
        }
        return project;
    }

    public ProjectDTO updateProject(Long code, ProjectDTO projectDTO) {
        Project projectToUpdate = projectRepository.findProjectById(code);

        if (!StringUtils.isEmpty(projectDTO.getName())) {
            projectToUpdate.setName(projectDTO.getName());
        }
        if (!StringUtils.isEmpty(projectDTO.getDescription())) {
            projectToUpdate.setDescription(projectDTO.getDescription());
        }
        if (Objects.nonNull(projectDTO.getStartDate())) {
            projectToUpdate.setStartDate(projectDTO.getStartDate());
        }
        if (Objects.nonNull(projectDTO.getEndDate())) {
            projectToUpdate.setEndDate(projectDTO.getEndDate());
        }
        if (Objects.nonNull(projectDTO.getStatus())) {
            ProjectStatus newProjectStatus = ProjectStatus.getStatusById(projectDTO.getStatus());
            checkAndSetProjectStatus(projectToUpdate, newProjectStatus);
        }
        Project updatedProject = projectRepository.save(projectToUpdate);
        return formatProject(updatedProject);
    }

    private void checkAndSetProjectStatus(Project project, ProjectStatus projectStatus) {
        project.getStatus().checkTransitionStatus(projectStatus);
        if (ProjectStatus.FINISHED.equals(projectStatus)) {
            project.setEndDate(new Date());
        }
        project.setStatus(projectStatus);
    }

    public void deleteProject(Long code) {
        Project projectToDelete = projectRepository.findProjectById(code);
        projectRepository.delete(projectToDelete);
    }

    public List<ProjectDTO> search(String name) {
        List<Project> projects = projectRepository.findProjectByNameContainingIgnoreCase(name);
        return projects.stream()
                .map(Project::convertToDTO)
                .collect(Collectors.toList());
    }
}
