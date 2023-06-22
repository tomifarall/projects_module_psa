package com.pca.projects.projects_module.service;

import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.model.WorkHoursRegister;
import com.pca.projects.projects_module.repository.ProjectRepository;
import com.pca.projects.projects_module.repository.WorkHoursRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class WorkHoursRegisterService {

    private final WorkHoursRegisterRepository workHoursRegisterRepository;

    @Autowired
    public WorkHoursRegisterService(final WorkHoursRegisterRepository workHoursRegisterRepository) {
        this.workHoursRegisterRepository = workHoursRegisterRepository;
    }

    public Collection<WorkHoursRegister> getWorkHoursByTask(ProjectTask task) {
        return workHoursRegisterRepository.findWorkHoursRegistersByTask(task);
    }

    public WorkHoursRegister save(WorkHoursRegister workHoursRegister) {
        return workHoursRegisterRepository.save(workHoursRegister);
    }

    public Optional<WorkHoursRegister> findById(Long id) {
        return workHoursRegisterRepository.findById(id);
    }
}
