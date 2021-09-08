package com.cleanup.todoc.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
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

    public final LiveData<List<RelationTaskWithProject>> allTasksLivedata;
    public final LiveData<List<RelationTaskWithProject>> allTasksLivedataAZ;
    public final LiveData<List<RelationTaskWithProject>> allTasksLivedataZA;
    public final LiveData<List<RelationTaskWithProject>> allTasksLivedataOld;
    public final LiveData<List<RelationTaskWithProject>> allTasksLivedataRecent;

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



    // not used at the moment
    public void delete(Task task) {
        doInBackground.execute(new Runnable() {
            @Override
            public void run() {
                mTaskDao.delete(task);
            }
        });
    }

    public void deleteTask(Task task) {
        //new DeleteTaskAsyncTask(mTaskDao).execute(task);
    }
    // not used at the moment
    public void deleteAllTask() {
       // new DeleteAllTasksAsyncTask(mTaskDao).execute();
    }

    // getters

    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedata() {
        return allTasksLivedata;
    }

    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataAZ() {
        return allTasksLivedataAZ;
    }

    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataZA() {
        return allTasksLivedataZA;
    }

    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataOld() {
        return allTasksLivedataOld;
    }

    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataRecent() {
        return allTasksLivedataRecent;
    }

}
