package com.pca.projects.projects_module.service.client;

import com.pca.projects.projects_module.service.client.DTO.VersionDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

@Service
public class SupportClientService extends ApiClient {
    private static final String VERSIONS_URL = "https://apisoporte.onrender.com/versiones/%d";

    private static final String DELETE_PROJECT_VERSION_URL = "https://apisoporte.onrender.com/versiones/%d/proyectos";

    private static final String DELETE_TASk_FROM_TICKET_URL = "https://apisoporte.onrender.com/tareasAsignadas/%d/tareas";

    public VersionDTO updateVersion(Long versionId, VersionDTO body) {
        VersionDTO versionDTO = null;
        try {
            versionDTO = put(String.format(VERSIONS_URL, versionId), body, VersionDTO.class);
        } catch (HttpStatusCodeException ex) {
            handleClientException(ex, "updateVersion");
        } catch (IOException ignored) {
        }
        return versionDTO;
    }

    public void deleteProjectFromVersion(Long versionId) {
        try {
            delete(String.format(DELETE_PROJECT_VERSION_URL, versionId), VersionDTO.class);
        } catch (HttpStatusCodeException ex) {
            handleClientException(ex, "deleteProjectFromVersion");
        } catch (IOException ignored) {
        }
    }

    public void deleteTaskFromTicket(Long taskId) {
        try {
            delete(String.format(DELETE_TASk_FROM_TICKET_URL, taskId), VersionDTO.class);
        } catch (HttpStatusCodeException ex) {
            handleClientException(ex, "deleteTaskFromTicket");
        } catch (IOException ignored) {
        }
    }
}
