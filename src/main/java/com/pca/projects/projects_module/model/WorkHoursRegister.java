package com.pca.projects.projects_module.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkHoursRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_of_work")
    private Date dateOfWork;

    @Column(name = "amount_hours")
    private Double amountHours;

    @Column(name = "employee_id")
    private Long employeeId;

    @ManyToOne(fetch=FetchType.LAZY)
    private ProjectTask task;
}
