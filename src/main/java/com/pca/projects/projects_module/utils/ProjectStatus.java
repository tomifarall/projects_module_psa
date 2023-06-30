package com.pca.projects.projects_module.utils;

import com.pca.projects.projects_module.exception.InvalidTransitionStatusException;

import java.util.Objects;

public enum ProjectStatus {
    STARTING("starting", "starting", "developing", "implementation", "finished"),
    DEVELOPING("developing", "developing", "implementation", "finished"),
    IMPLEMENTATION("implementation", "developing", "implementation", "finished"),
    FINISHED("finished",  "finished");

    private final String id;

    private final String[] availableNextStatus;

    ProjectStatus(String id, String... availableNextStatus) {
        this.id = id;
        this.availableNextStatus = availableNextStatus;
    }

    public String getId() {
        return this.id;
    }


    public static ProjectStatus getStatusById(String id) {
        for (ProjectStatus projectStatus : ProjectStatus.values()) {
            if (Objects.equals(projectStatus.getId(), id)) {
                return projectStatus;
            }
        }
        throw new EnumConstantNotPresentException(ProjectStatus.class, id);
    }

    public void checkTransitionStatus(ProjectStatus newStatus) {
        for (String availableNextStatus : availableNextStatus) {
            if (availableNextStatus.equals(newStatus.getId())) {
                return;
            }
        }
        throw new InvalidTransitionStatusException("Invalid transition status");
    }

}
