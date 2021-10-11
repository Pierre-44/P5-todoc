package com.cleanup.todoc.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.entity.Project;

import java.util.List;

/**
 * Created by pmeignen on 30/08/2021.
 */
@Dao
public interface ProjectDao {

    /**
     * Insert.
     *
     * @param project the project
     */
    @Insert
    void insert(Project project);

    /**
     * Delete.
     *
     * @param project the project
     */
    @Delete
    void delete(Project project);

    /**
     * Gets all projects.
     *
     * @return all projects ordered by id
     */
    @Query("SELECT * FROM project_table ORDER BY id")
    LiveData<List<Project>> getAllProjects();

    /**
     * Delete all projects from table.
     */
    @Query("DELETE FROM project_table")
    void deleteAllProjects();
}
