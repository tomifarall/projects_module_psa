package com.pca.projects.projects_module.service.client;

import com.pca.projects.projects_module.service.client.DTO.VersionDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;

@Service
public class SupportClientService extends ApiClient {
    private static final String VERSIONS_URL = "https://apisoporte.onrender.com/versiones/%d";

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

    public VersionDTO getVersion(Long versionId) {
        VersionDTO versionDTO = null;
        try {
            versionDTO = get(String.format(VERSIONS_URL, versionId), VersionDTO.class);
        } catch (HttpStatusCodeException ex) {
            handleClientException(ex, "getVersion");
        } catch (IOException ignored) {
        }
        return versionDTO;
    }
}
