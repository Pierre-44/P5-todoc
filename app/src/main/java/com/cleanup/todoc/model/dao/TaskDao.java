package com.cleanup.todoc.model.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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

    @Update
    void update(Task task);


    @Query("SELECT * FROM task_table ORDER BY task_id ")
    LiveData<List<Task>> getAllTasks();


    @Query("SELECT * FROM task_table ORDER BY task_creation_time_stamp ASC")
    LiveData<List<Task>> getAllTasksByTimeStampRecent();


    @Query("SELECT * FROM task_table ORDER BY task_creation_time_stamp DESC")
    LiveData<List<Task>> getAllTasksByTimeStampOld();


    @Query("SELECT * FROM task_table ORDER BY task_name ASC")
    LiveData<List<Task>> getAllTasksByNameAZ();

    @Transaction
    @Query("SELECT * FROM task_table ORDER BY task_name DESC")
    LiveData<List<Task>> getAllTasksByNameZA();


}