package com.cleanup.todoc.model.entity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_table")
public class Project {


    /**
     * The unique identifier of the project
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long projectId;

    /**
     * The name of the project
     */
    @NonNull
    @ColumnInfo(name = "name")
    private final String projectName;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    @ColumnInfo(name = "color")
    private final int projectColor;

    /**
     * Instantiates a new Project.
     *
     * @param projectName  the name of the project to set
     * @param projectColor the hex (ARGB) code of the color associated to the project to set
     */
    public Project(@NonNull String projectName, @ColorInt int projectColor) {
        this.projectName = projectName;
        this.projectColor = projectColor;
    }


    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Set id.
     *
     * @param id the id of the project
     */
    public void setProjectId(long id){
        this.projectId = id;
    }

    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the hex (ARGB) code of the color associated to the project.
     *
     * @return the hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    public int getProjectColor() {
        return projectColor;
    }

    @Override
    @NonNull
    public String toString() {
        return getProjectName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (getProjectId() != project.getProjectId()) return false;
        if (getProjectColor() != project.getProjectColor()) return false;
        return getProjectName().equals(project.getProjectName());
    }
}
