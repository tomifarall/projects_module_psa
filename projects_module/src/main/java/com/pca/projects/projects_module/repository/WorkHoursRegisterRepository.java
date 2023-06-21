package com.pca.projects.projects_module.repository;

import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface WorkHoursRegisterRepository extends JpaRepository<WorkHoursRegister, Long> {

    Collection<WorkHoursRegister> findWorkHoursRegistersByTask(ProjectTask task);
}
