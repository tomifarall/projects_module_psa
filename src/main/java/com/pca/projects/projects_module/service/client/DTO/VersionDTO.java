package com.pca.projects.projects_module.service.client.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VersionDTO {

    @JsonProperty("idVersion")
    private Long id;

    @JsonProperty("idProyecto")
    private Long projectId;

    @JsonProperty("CodigoVersion")
    private String versionCode;
}
