package com.cleanup.todoc.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cleanup.todoc.model.entity.RelationTaskWithProject;
import com.cleanup.todoc.model.entity.Task;

import java.util.List;

/**
 * Created by pmeignen on 30/08/2021.
 */
@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);


    @Query("DELETE FROM task_table")
    void deleteAllTasks();

    @Transaction
    @Query("SELECT * FROM task_table ORDER BY id ")
    LiveData<List<RelationTaskWithProject>> getAllTasks();

    @Transaction
    @Query("SELECT * FROM task_table ORDER BY creation_time_stamp ASC")
    LiveData<List<RelationTaskWithProject>> getAllTasksByTimeStampRecent();

    @Transaction
    @Query("SELECT * FROM task_table ORDER BY creation_time_stamp DESC")
    LiveData<List<RelationTaskWithProject>> getAllTasksByTimeStampOld();

    @Transaction
    @Query("SELECT * FROM task_table ORDER BY name ASC")
    LiveData<List<RelationTaskWithProject>> getAllTasksByNameAZ();

    @Transaction
    @Query("SELECT * FROM task_table ORDER BY name DESC")
    LiveData<List<RelationTaskWithProject>> getAllTasksByNameZA();

}