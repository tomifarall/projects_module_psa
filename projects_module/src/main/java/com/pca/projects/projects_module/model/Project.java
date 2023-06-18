package com.pca.projects.projects_module.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    private String name;

    private String description;

    private Integer status;

    private Date startDate;

    private Date endDate;

    private Double hoursWorked;

    private Double tasksQuantity;

    public Project(){
    }

    public Project(String name, String description, Integer status, Date startDate, Date endDate, Double hoursWorked, Double tasksQuantity){
        this.name = name;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hoursWorked = hoursWorked;
        this.tasksQuantity = tasksQuantity;
    }

    public Long getCode(){
        return this.code;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
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

    public Double getHoursWorked(){
        return this.hoursWorked;
    }
    public void setHoursWorked(Double hoursWorked){
        this.hoursWorked = hoursWorked;
    }

    public Double getTasksQuantity(){
        return this.tasksQuantity;
    }
    public void setTasksQuantity(Double tasksQuantity){
        this.tasksQuantity = tasksQuantity;
    }

}
