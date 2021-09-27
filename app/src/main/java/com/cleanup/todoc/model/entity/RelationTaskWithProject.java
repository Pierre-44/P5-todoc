package com.cleanup.todoc.model.entity;

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


    //--------------------------------------------------
    // Task Comparator
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