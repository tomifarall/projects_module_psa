package com.pca.projects.projects_module.repository;

import com.pca.projects.projects_module.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findProjectById(Long id);
}
