package com.pca.projects.projects_module.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer type_id;
    private Integer priority;
    private Integer status;
    private Date startDate;
    private Date endDate;
    private Double estimatedTime;
    private Double timeWorked;

    public ProjectTask(){
    }

    public ProjectTask(String title, String description, Integer type_id, Integer priority, Integer status, Date startDate, Date endDate, Double estimatedTime){
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
    public Integer getStatus(){
        return this.status;
    }
    public void setStatus(Integer status){
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
    public Double getTimeWorked(){
        return this.timeWorked;
    }
    public void setTimeWorked(Double timeWorked){
        this.timeWorked = timeWorked;
    }
}
