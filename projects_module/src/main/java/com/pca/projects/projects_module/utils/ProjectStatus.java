package com.pca.projects.projects_module.utils;

import java.util.Objects;

public enum ProjectStatus {
    PENDING("pending"),
    WORKING("working"),
    FINISHED("finished");

    private String id;
    ProjectStatus(String id) {
        this.id = id;
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

}
