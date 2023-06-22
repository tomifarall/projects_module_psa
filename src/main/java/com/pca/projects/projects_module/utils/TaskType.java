package com.pca.projects.projects_module.utils;

import java.util.Objects;

public enum TaskType {

    FEATURE("feature"),
    BUG("bug");

    private final String id;
    TaskType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }


    public static TaskType getTaskTypeById(String id) {
        for (TaskType taskType : TaskType.values()) {
            if (Objects.equals(taskType.getId(), id)) {
                return taskType;
            }
        }
        throw new EnumConstantNotPresentException(TaskType.class, id);
    }
}
