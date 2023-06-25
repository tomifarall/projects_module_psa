package com.pca.projects.projects_module.repository;

import com.pca.projects.projects_module.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findProjectById(Long id);

    List<Project> findProjectByNameContaining(String name);
}
