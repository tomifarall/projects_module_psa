package com.pca.projects.projects_module.service.client.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResourceDTO {

    @JsonProperty("legajo")
    private Long id;

    @JsonProperty("Nombre")
    private String name;

    @JsonProperty("Apellido")
    private String lastname;
}
