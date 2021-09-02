package com.cleanup.todoc.model.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Task;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class TaskRepository {

    private final TaskDao mTaskDao;

    public final LiveData<List<Task>> allTasksLivedata;
    public final LiveData<List<Task>> allTasksLivedataAZ;
    public final LiveData<List<Task>> allTasksLivedataZA;
    public final LiveData<List<Task>> allTasksLivedataOld;
    public final LiveData<List<Task>> allTasksLivedataRecent;

    private final Executor doInBackground;

    public TaskRepository(@NonNull TaskDao taskDao) {
        mTaskDao = taskDao;
        doInBackground = Executors.newFixedThreadPool(3);
        allTasksLivedata = taskDao.getAllTasks();
        allTasksLivedataAZ = taskDao.getAllTasksByNameAZ();
        allTasksLivedataZA = taskDao.getAllTasksByNameZA();
        allTasksLivedataOld = taskDao.getAllTasksByTimeStampOld();
        allTasksLivedataRecent = taskDao.getAllTasksByTimeStampRecent();
    }

    public void insert(Task task) {
        doInBackground.execute(() -> mTaskDao.insert(task));
    }

    public void update(Task task) {
        doInBackground.execute(() -> mTaskDao.update(task));
    }

    public void delete(Task task) {
        doInBackground.execute(() -> mTaskDao.delete(task));
    }

}
