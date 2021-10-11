package com.cleanup.todoc.model.entity;

import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Comparator;

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

    /**
     * Gets task.
     *
     * @return the task to get
     */
    public Task getTask() {
        return mTask;
    }

    /**
     * Sets task.
     *
     * @param task the task to set
     */
    public void setTask(Task task) {
        this.mTask = task;
    }

    /**
     * Gets project.
     *
     * @return the project to get
     */
    public Project getProject() {
        return mProject;
    }

    /**
     * Sets project.
     *
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.mProject = project;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }


    //--------------------------------------------------
    // RelationTaskWithProject Comparator
    //--------------------------------------------------

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<RelationTaskWithProject> {
        @Override
        public int compare(RelationTaskWithProject left, RelationTaskWithProject right) {
            return (left.getTask().getTaskName()).compareTo(right.getTask().getTaskName());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<RelationTaskWithProject> {
        @Override
        public int compare(RelationTaskWithProject left, RelationTaskWithProject right) {
            return (right.getTask().getTaskName()).compareTo(left.getTask().getTaskName());
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<RelationTaskWithProject> {
        @Override
        public int compare(RelationTaskWithProject left, RelationTaskWithProject right) {
            return (int) (right.getTask().getCreationTimestamp() - left.getTask().getCreationTimestamp());
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<RelationTaskWithProject> {
        @Override
        public int compare(RelationTaskWithProject left, RelationTaskWithProject right) {
            return (int) (left.getTask().getCreationTimestamp() - right.getTask().getCreationTimestamp());
        }
    }
}