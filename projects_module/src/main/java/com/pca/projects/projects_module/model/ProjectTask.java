package com.pca.projects.projects_module.model;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.utils.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer type_id;
    private Integer priority;
    private TaskStatus status;
    private Date startDate;
    private Date endDate;
    private Double estimatedTime;
    private String employeeId;

    @ManyToOne(fetch=FetchType.LAZY)
    private Project project;

    @OneToMany(mappedBy="task")
    private List<WorkHoursRegister> timeWorked;

    public ProjectTask(){
    }

    public ProjectTask(String title, String description, Integer type_id, Integer priority, TaskStatus status, Date startDate, Date endDate, Double estimatedTime){
        this.title = title;
        this.description = description;
        this.type_id = type_id;
        this.priority = priority;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedTime = estimatedTime;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Integer getTypeId(){
        return this.type_id;
    }
    public void setTypeId(Integer type_id){
        this.type_id = type_id;
    }
    public Integer getPriority(){
        return this.priority;
    }
    public void setPriority(Integer priority){
        this.priority = priority;
    }
    public TaskStatus getStatus(){
        return this.status;
    }
    public void setStatus(TaskStatus status){
        this.status = status;
    }
    public Date getStartDate(){
        return this.startDate;
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public Date getEndDate(){
        return this.endDate;
    }
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }
    public Double getEstimatedTime(){
        return this.estimatedTime;
    }
    public void setEstimatedTime(Double estimatedTime){
        this.estimatedTime = estimatedTime;
    }

    public TaskDTO convertToDTO() {
        return TaskDTO.builder()
                .id(id)
                .title(title)
                .description(description)
                .type_id(type_id)
                .priority(priority)
                .status(status.getId())
                .startDate(startDate)
                .endDate(endDate)
                .estimatedTime(estimatedTime)
                .employeeId(employeeId)
                .project(project)
                .timeWorked(timeWorked)
                .build();
    }
}
