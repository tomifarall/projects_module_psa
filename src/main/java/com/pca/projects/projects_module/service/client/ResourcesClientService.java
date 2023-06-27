package com.pca.projects.projects_module.service.client;

import com.pca.projects.projects_module.service.client.DTO.ResourceDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

@Service
public class ResourcesClientService extends ApiClient {
    private static final String RESOURCE_URL = "/recursos/%d";

    public ResourceDTO getResource(Long resourceId) throws IOException {
        ResourceDTO resourceDTO = null;
        try {
             resourceDTO = get(String.format(RESOURCE_URL, resourceId), ResourceDTO.class);
        } catch (HttpStatusCodeException ex) {
            handleClientException(ex, "getResource");
        }
        return resourceDTO;
    }
}
