package com.cleanup.todoc.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.cleanup.todoc.model.entity.RelationTaskWithProject;
import com.cleanup.todoc.model.entity.Task;

import java.util.List;

/**
 * Created by pmeignen on 30/08/2021.
 */
@Dao
public interface TaskDao {

    /**
     * Insert Task.
     *
     * @param task the task to insert
     */
    @Insert
    void insert(Task task);

    /**
     * Delete Task.
     *
     * @param task the task to delete
     */
    @Delete
    void delete(Task task);

    /**
     * Update Task.
     *
     * @param task the task to update
     */
    @Update
    void update(Task task);

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM task_table")
    void deleteAllTasks();

    /**
     * Delete task by id.
     *
     * @param taskId the id of task to delete
     */
    @Transaction
    @Query("DELETE FROM task_table WHERE id = :taskId")
    void deleteTaskById(long taskId);

    /**
     * Gets all tasks.
     *
     * @return the all tasks ordered by id
     */
    @Transaction
    @Query("SELECT * FROM task_table ORDER BY id ")
    LiveData<List<RelationTaskWithProject>> getAllTasks();

    /**
     * Gets all tasks by time stamp recent.
     *
     * @return the all tasks by time stamp recent
     */
    @Transaction
    @Query("SELECT * FROM task_table ORDER BY creation_time_stamp ASC")
    LiveData<List<RelationTaskWithProject>> getAllTasksByTimeStampRecent();

    /**
     * Gets all tasks by time stamp old.
     *
     * @return the all tasks by time stamp old
     */
    @Transaction
    @Query("SELECT * FROM task_table ORDER BY creation_time_stamp DESC")
    LiveData<List<RelationTaskWithProject>> getAllTasksByTimeStampOld();

    /**
     * Gets all tasks by name az.
     *
     * @return the all tasks by name az
     */
    @Transaction
    @Query("SELECT * FROM task_table ORDER BY name ASC")
    LiveData<List<RelationTaskWithProject>> getAllTasksByNameAZ();

    /**
     * Gets all tasks by name za.
     *
     * @return the all tasks by name za
     */
    @Transaction
    @Query("SELECT * FROM task_table ORDER BY name DESC")
    LiveData<List<RelationTaskWithProject>> getAllTasksByNameZA();
}