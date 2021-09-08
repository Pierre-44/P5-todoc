package com.cleanup.todoc.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * Created by pmeignen on 08/09/2021.
 */
public class RelationTaskWithProject {
    @Embedded
    private Task mTask;
    @Relation(
            parentColumn = "project_id",
            entityColumn = "id",
            entity = Project.class)

    private Project mProject;

    public Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        this.mTask = task;
    }

    public Project getProject() {
        return mProject;
    }
    public void setProject(Project project) {
        this.mProject = project;
    }
}
