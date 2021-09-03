package com.cleanup.todoc.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Task;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class TaskRepository {

    //fields
    private final TaskDao mTaskDao;
    private final Executor doInBackground;

    public final LiveData<List<Task>> allTasksLivedata;
    public final LiveData<List<Task>> allTasksLivedataAZ;
    public final LiveData<List<Task>> allTasksLivedataZA;
    public final LiveData<List<Task>> allTasksLivedataOld;
    public final LiveData<List<Task>> allTasksLivedataRecent;

    // Constructor
    public TaskRepository(Application application) {

        TodocDatabase todocDatabase = TodocDatabase.getInstance(application);
        mTaskDao = todocDatabase.mTaskDao();
        allTasksLivedata = mTaskDao.getAllTasks();
        allTasksLivedataAZ = mTaskDao.getAllTasksByNameAZ();
        allTasksLivedataZA = mTaskDao.getAllTasksByNameZA();
        allTasksLivedataOld = mTaskDao.getAllTasksByTimeStampOld();
        allTasksLivedataRecent = mTaskDao.getAllTasksByTimeStampRecent();
        doInBackground = Executors.newFixedThreadPool(4);
    }
    // methods of interface

    public void insert(Task task) {
        doInBackground.execute(() -> mTaskDao.insert(task));
    }

    public void update(Task task) {
        doInBackground.execute(() -> mTaskDao.update(task));
    }

    public void delete(Task task) {
        doInBackground.execute(() -> mTaskDao.delete(task));
    }

    // getters

    public LiveData<List<Task>> getAllTasksLivedata() {
        return allTasksLivedata;
    }

    public LiveData<List<Task>> getAllTasksLivedataAZ() {
        return allTasksLivedataAZ;
    }

    public LiveData<List<Task>> getAllTasksLivedataZA() {
        return allTasksLivedataZA;
    }

    public LiveData<List<Task>> getAllTasksLivedataOld() {
        return allTasksLivedataOld;
    }

    public LiveData<List<Task>> getAllTasksLivedataRecent() {
        return allTasksLivedataRecent;
    }



}
