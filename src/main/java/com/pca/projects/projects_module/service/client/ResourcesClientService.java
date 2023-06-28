package com.pca.projects.projects_module.service.client;

import com.pca.projects.projects_module.service.client.DTO.HoursRegisterDTO;
import com.pca.projects.projects_module.service.client.DTO.ResourceDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResourcesClientService extends ApiClient {
    private static final String RESOURCE_URL = "https://rrhh-squad6-1c2023.onrender.com//recursos/%d";

    private static final String RESOURCES_REGISTER_BY_TASK_URL = "https://rrhh-squad6-1c2023.onrender.com//recursos/%d/registros?idProyecto=%d&idTarea=%d";

    public ResourceDTO getResource(Long resourceId) {
        ResourceDTO resourceDTO = null;
        try {
             resourceDTO = get(String.format(RESOURCE_URL, resourceId), ResourceDTO.class);
        } catch (HttpStatusCodeException ex) {
            handleClientException(ex, "getResource");
        } catch (IOException ignored) {

        }
        return resourceDTO;
    }

    public List<HoursRegisterDTO> getResourceRegistersByTask(Long resourceId, Long projectId, Long taskId) {
        HoursRegisterDTO[] hoursRegisterDTOS = null;
        try {
            hoursRegisterDTOS = get(String.format(RESOURCES_REGISTER_BY_TASK_URL, resourceId, projectId, taskId), HoursRegisterDTO[].class);
        } catch (HttpStatusCodeException ex) {
            handleClientException(ex, "getResourceRegistersByTask");
        } catch (IOException ignored) {

        }
        return Objects.nonNull(hoursRegisterDTOS)
                ? Arrays.stream(hoursRegisterDTOS).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
