package com.pca.projects.projects_module.repository;

import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

    List<ProjectTask> findProjectTasksByProject(Project project);

    List<ProjectTask> findProjectTasksByTitleContainingIgnoreCase(String name);

    ProjectTask findProjectTaskById(Long id);

}
