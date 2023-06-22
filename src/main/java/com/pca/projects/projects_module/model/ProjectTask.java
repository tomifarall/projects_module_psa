package com.pca.projects.projects_module.model;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.utils.TaskPriority;
import com.pca.projects.projects_module.utils.TaskStatus;
import com.pca.projects.projects_module.utils.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_task")
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "task_type")
    private TaskType taskType;

    @Column(name = "task_priority")
    private TaskPriority taskPriority;

    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "estimated_time")
    private Double estimatedTime;

    @Column(name = "employee_id")
    private Long employeeId;

    @ManyToOne(fetch=FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy="task")
    private List<WorkHoursRegister> timeWorked;

    public TaskDTO convertToDTO() {
        ProjectDTO projectDTO = ProjectDTO.builder()
                .name(project.getName())
                .build();

        return TaskDTO.builder()
                .id(id)
                .title(title)
                .description(description)
                .taskType(taskType.getId())
                .taskPriority(taskPriority.getId())
                .status(status.getId())
                .startDate(startDate)
                .endDate(endDate)
                .estimatedTime(estimatedTime)
                .employeeId(employeeId)
                .project(projectDTO)
                .timeWorked(timeWorked)
                .build();
    }
}
