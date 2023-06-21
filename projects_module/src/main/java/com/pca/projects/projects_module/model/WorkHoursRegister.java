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
    private Long id;

    private Date dateOfWork;

    private Double amountHours;

    private String employeeId;

    @ManyToOne(fetch=FetchType.LAZY)
    private ProjectTask task;
}
