package com.pca.projects.projects_module.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class WorkHoursRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateOfWork;
    private Double amountHours;
    private String employeeId;

    @ManyToOne(fetch=FetchType.LAZY)
    private ProjectTask task;

    public WorkHoursRegister(){
    }

    public WorkHoursRegister(Date dateOfWork, Double amountHours, String employeeId) {
        this.dateOfWork = dateOfWork;
        this.amountHours = amountHours;
        this.employeeId = employeeId;
    }

}
