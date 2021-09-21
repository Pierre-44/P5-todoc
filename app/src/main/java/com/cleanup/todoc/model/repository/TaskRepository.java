package com.cleanup.todoc.model.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Project;
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
    private final ProjectDao mProjectDao;
    private final Executor doInBackground;

    private LiveData<List<Project>> allProjects;
    public final LiveData<List<RelationTaskWithProject>> allTasks;
    public final LiveData<List<RelationTaskWithProject>> allTasksLivedataAZ;
    public final LiveData<List<RelationTaskWithProject>> allTasksLivedataZA;
    public final LiveData<List<RelationTaskWithProject>> allTasksLivedataOld;
    public final LiveData<List<RelationTaskWithProject>> allTasksLivedataRecent;

    // Constructor
    public TaskRepository(Context context) {

        TodocDatabase todocDatabase = TodocDatabase.getInstance(context);
        mTaskDao = todocDatabase.mTaskDao();
        mProjectDao = todocDatabase.mProjectDao();
        allProjects = mProjectDao.getAllProjects();
        allTasks = mTaskDao.getAllTasks();
        allTasksLivedataAZ = mTaskDao.getAllTasksByNameAZ();
        allTasksLivedataZA = mTaskDao.getAllTasksByNameZA();
        allTasksLivedataOld = mTaskDao.getAllTasksByTimeStampOld();
        allTasksLivedataRecent = mTaskDao.getAllTasksByTimeStampRecent();
        doInBackground = Executors.newFixedThreadPool(4);
    }
    // methods of interface

    //public void insert(Task task){
    //    mTaskDao.insert(task);
    //}

    public void deleteTaskById(int id){
        mTaskDao.deleteTaskById(id);
    }



    public void insert(Task task) {
        doInBackground.execute(() -> mTaskDao.insert(task));
    }

    public void delete(Task task) {
        doInBackground.execute(() -> mTaskDao.delete(task));
    }

    public void deleteTaskByID(int taskId) {
        mTaskDao.deleteTaskById(taskId);
    }

    public void update(Task task) {
        doInBackground.execute(() -> mTaskDao.update(task));
    }

    public void deleteAllTask() {
        doInBackground.execute(mTaskDao::deleteAllTasks);
    }


    // getter

    public LiveData<List<RelationTaskWithProject>> getAllTasks() {
        return allTasks;
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
