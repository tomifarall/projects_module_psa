package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.exception.InvalidProjectException;
import com.pca.projects.projects_module.exception.ProjectNotFoundException;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.repository.ProjectRepository;
import com.pca.projects.projects_module.utils.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Collection<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        Project project =  projectRepository.findProjectById(id);
        if (Objects.isNull(project)) {
            throw new ProjectNotFoundException(String.format("Project %d not found", id));
        }
        return project;
    }

    @Transactional
    public ProjectDTO createToDTO(Project project) {
        Project createdProject = create(project);
        return createdProject.convertToDTO();

    }

    @Transactional
    public Project create(Project project) {
        validateProjectData(project);
        project.setStatus(ProjectStatus.PENDING);
        project.setHoursWorked(0D);
        project.setTasksQuantity(0);
        projectRepository.save(project);
        return project;
    }

    private void validateProjectData(Project project) {
        boolean isProjectInvalid = StringUtils.isEmpty(project.getName())
                || StringUtils.isEmpty(project.getDescription())
                || Objects.isNull(project.getStartDate())
                || Objects.isNull(project.getEndDate());

        if (isProjectInvalid) throw new InvalidProjectException("Project data is invalid.");
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
            projectToUpdate.setStatus(ProjectStatus.getStatusById(projectDTO.getStatus()));
        }

        projectRepository.saveAndFlush(projectToUpdate);
        return projectToUpdate.convertToDTO();
    }

    public void deleteProject(Long code) {
        Project projectToDelete = projectRepository.findProjectById(code);
        //TODO: ALGUNA VERIFICACIÓN??
        projectRepository.delete(projectToDelete);
    }
}
