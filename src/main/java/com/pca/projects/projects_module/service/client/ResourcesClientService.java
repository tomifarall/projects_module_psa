package com.pca.projects.projects_module.service.client;

import com.pca.projects.projects_module.service.client.DTO.ResourceDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ResourcesClientService extends ApiClient {

    private static final String ALL_RESOURCES_URL = "/recursos";

    private static final String RESOURCE_URL = "/recursos/%d";

    public ResourceDTO getAllResources() throws IOException {
        return get(ALL_RESOURCES_URL, ResourceDTO.class);
    }

    public ResourceDTO getResource(Long resourceId) throws IOException {
        return get(String.format(RESOURCE_URL, resourceId), ResourceDTO.class);
    }
}
