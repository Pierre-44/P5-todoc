package com.cleanup.todoc.model.repository;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
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
    private ProjectDao mProjectDao;
    private Executor doInBackground;

    private LiveData<List<Project>> allProjects;
    public LiveData<List<RelationTaskWithProject>> allTasks;
    public LiveData<List<RelationTaskWithProject>> allTasksLivedataAZ;
    public LiveData<List<RelationTaskWithProject>> allTasksLivedataZA;
    public LiveData<List<RelationTaskWithProject>> allTasksLivedataOld;
    public LiveData<List<RelationTaskWithProject>> allTasksLivedataRecent;

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
        doInBackground = Executors.newFixedThreadPool(2);
    }

    @VisibleForTesting
    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
        doInBackground = Executors.newFixedThreadPool(2);
    }

    // methods of interface

    public void insert(Task task) {
        doInBackground.execute(() -> mTaskDao.insert(task));
    }

    public void delete(Task task) {
        doInBackground.execute(() -> mTaskDao.delete(task));
    }

    public void deleteTaskById(long taskId) {
        doInBackground.execute(() -> mTaskDao.deleteTaskById(taskId));
    }

    public void update(Task task) {
        doInBackground.execute(() -> mTaskDao.update(task));
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

    // setter
    public void setDoInBackground(Executor doInBackground) {
        this.doInBackground = doInBackground;
    }
}
