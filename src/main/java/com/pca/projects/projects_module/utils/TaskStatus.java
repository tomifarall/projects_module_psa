package com.pca.projects.projects_module.utils;

import java.util.Objects;

public enum TaskStatus {
    PENDING("pending"),
    WORKING("working"),
    REVIEWING("reviewing"),
    FINISHED("finished");

    private String id;
    TaskStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }


    public static TaskStatus getStatusById(String id) {
        for (TaskStatus projectStatus : TaskStatus.values()) {
            if (Objects.equals(projectStatus.getId(), id)) {
                return projectStatus;
            }
        }
        throw new EnumConstantNotPresentException(TaskStatus.class, id);
    }

}
