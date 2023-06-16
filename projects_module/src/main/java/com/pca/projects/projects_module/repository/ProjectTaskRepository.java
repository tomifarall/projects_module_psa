package com.pca.projects.projects_module.repository;

import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
}
