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

    @Insert
    void insert(Project project);

    @Delete
    void delete(Project project);

    @Query("SELECT * FROM project_table ORDER BY project_id")
    LiveData<List<Project>> getAllProjects();


}
