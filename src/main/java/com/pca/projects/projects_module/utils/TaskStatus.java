package com.pca.projects.projects_module.utils;

import com.pca.projects.projects_module.exception.InvalidTransitionStatusException;
import java.util.Objects;

public enum TaskStatus {
    PENDING("pending", "pending", "working", "reviewing", "finished"),
    WORKING("working", "working", "reviewing", "finished"),
    REVIEWING("reviewing", "working", "reviewing", "finished"),
    FINISHED("finished", "finished");

    private final String id;

    private final String[] availableNextStatus;


    TaskStatus(String id, String... availableNextStatus) {
        this.id = id;
        this.availableNextStatus = availableNextStatus;
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

    public void checkTransitionStatus(TaskStatus newStatus) {
        for (String availableNextStatus : availableNextStatus) {
            if (availableNextStatus.equals(newStatus.getId())) {
                return;
            }
        }
        throw new InvalidTransitionStatusException("Invalid transition status");
    }
}
