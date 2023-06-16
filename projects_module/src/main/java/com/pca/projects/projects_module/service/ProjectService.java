package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
}
