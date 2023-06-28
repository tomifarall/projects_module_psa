package com.pca.projects.projects_module.service.client.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HoursRegisterDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fecha_de_registro")
    private Date registerDate;

    @JsonProperty("legajo_recurso")
    private Long resourceId;

    @JsonProperty("id_tarea")
    private Long taskId;

    @JsonProperty("id_proyecto")
    private Long projectId;

    @JsonProperty("cantidad")
    private Double hours;
}
