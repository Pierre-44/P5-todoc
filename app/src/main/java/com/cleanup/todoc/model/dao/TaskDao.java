package com.cleanup.todoc.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.cleanup.todoc.model.entity.Task;

/**
 * Created by pmeignen on 30/08/2021.
 */
@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

    LiveData<List<TasksWithProject>> getTasksWithProject();

// TODO : add transaction and query



}
