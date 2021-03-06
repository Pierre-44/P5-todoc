package com.cleanup.todoc.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * The type Task.
 */
@Entity(tableName = "task_table", foreignKeys = {@ForeignKey(
        entity = Project.class,
        parentColumns = "id",
        childColumns = "project_id")
})

public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long taskId;
    /**
     * The unique identifier of the project associated to the task
     * and add to db index
     */
    @ColumnInfo(name = "project_id", index = true)
    private long projectId;
    /**
     * The name of the task
     */
    @ColumnInfo(name = "name")
    private String taskName;
    /**
     * The timestamp when the task has been created
     */
    @ColumnInfo(name = "creation_time_stamp")
    private long creationTimestamp;


    /**
     * Instantiates a new Task.
     *
     * @param taskId            the task id (autogenerate by Room)
     * @param projectId         the project id
     * @param taskName          the task name
     * @param creationTimestamp the creation timestamp
     */
    public Task(
            long taskId,
            long projectId,
            @NonNull String taskName,
            long creationTimestamp
    ) {

        this.setTaskId(taskId);
        this.setProjectId(projectId);
        this.setTaskName(taskName);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getTaskId() {
        return taskId;
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the unique identifier of the task to set
     */
    public void setTaskId(long id) {
        this.taskId = id;
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getTaskName() {
        return taskName;
    }

    /**
     * Sets the name of the task.
     *
     * @param taskName the name of the task to set
     */
    private void setTaskName(@NonNull String taskName) {
        this.taskName = taskName;
    }

    /**
     * Gets project id.
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the unique identifier of the project associated to the task.
     *
     * @param projectId the unique identifier of the project associated to the task to set
     */
    private void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets creation timestamp.
     *
     * @return the creation timestamp
     */
    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * Sets the timestamp when the task has been created.
     *
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    private void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return taskId == task.taskId && projectId == task.projectId && creationTimestamp == task.creationTimestamp && Objects.equals(taskName, task.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, projectId, taskName, creationTimestamp);
    }
}
