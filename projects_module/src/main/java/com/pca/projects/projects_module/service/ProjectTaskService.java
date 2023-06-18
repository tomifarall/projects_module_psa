package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;

    @Autowired
    public ProjectTaskService(final ProjectTaskRepository projectTaskRepository) {
        this.projectTaskRepository = projectTaskRepository;
    }

    public List<ProjectTask> getProjectTasks() {
        return projectTaskRepository.findAll();
    }

    public Optional<ProjectTask> findById(Long id) {
        return projectTaskRepository.findById(id);
    }
}
