package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;

    @Autowired
    public ProjectTaskService(final ProjectTaskRepository projectTaskRepository) {
        this.projectTaskRepository = projectTaskRepository;
    }
}
