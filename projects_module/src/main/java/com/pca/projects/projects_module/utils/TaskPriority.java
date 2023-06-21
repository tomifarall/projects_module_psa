package com.pca.projects.projects_module.utils;

import java.util.Objects;

public enum TaskPriority {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private final String id;
    TaskPriority(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }


    public static TaskPriority getTaskTypeById(String id) {
        for (TaskPriority taskPriority : TaskPriority.values()) {
            if (Objects.equals(taskPriority.getId(), id)) {
                return taskPriority;
            }
        }
        throw new EnumConstantNotPresentException(TaskPriority.class, id);
    }
}
