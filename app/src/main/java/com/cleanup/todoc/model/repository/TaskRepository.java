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
    /**
     * The All tasks.
     */
    public LiveData<List<RelationTaskWithProject>> allTasks;
    /**
     * The All tasks livedata az.
     */
    public LiveData<List<RelationTaskWithProject>> allTasksLivedataAZ;
    /**
     * The All tasks livedata za.
     */
    public LiveData<List<RelationTaskWithProject>> allTasksLivedataZA;
    /**
     * The All tasks livedata old.
     */
    public LiveData<List<RelationTaskWithProject>> allTasksLivedataOld;
    /**
     * The All tasks livedata recent.
     */
    public LiveData<List<RelationTaskWithProject>> allTasksLivedataRecent;

    /**
     * Instantiates a new Task repository.
     *
     * @param context the context
     */
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

    /**
     * Instantiates a new Task repository.
     *
     * @param taskDao the task dao
     */
    @VisibleForTesting
    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
        doInBackground = Executors.newFixedThreadPool(2);
    }

    // methods of interface

    /**
     * Insert.
     *
     * @param task the task to insert
     */
    public void insert(Task task) {
        doInBackground.execute(() -> mTaskDao.insert(task));
    }

    /**
     * Delete.
     *
     * @param task the task to delete
     */
    public void delete(Task task) {
        doInBackground.execute(() -> mTaskDao.delete(task));
    }

    /**
     * Delete task by id.
     *
     * @param taskId the id of task to delete
     */
    public void deleteTaskById(long taskId) {
        doInBackground.execute(() -> mTaskDao.deleteTaskById(taskId));
    }


    /**
     * Update. (not use yet)
     *
     * @param task the task to update
     *
     */
    public void update(Task task) {
        doInBackground.execute(() -> mTaskDao.update(task));
    }

    // getter

    /**
     * Gets all relation task with project.
     *
     * @return the all relation task with project to get
     */
    public LiveData<List<RelationTaskWithProject>> getAllRelationTaskWithProjectLiveData() {
        return allTasks;
    }

    /**
     * Gets all tasks livedata az.
     *
     * @return the all tasks livedata az to get
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataAZ() {
        return allTasksLivedataAZ;
    }

    /**
     * Gets all tasks livedata za.
     *
     * @return the all tasks livedata za to get
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataZA() {
        return allTasksLivedataZA;
    }

    /**
     * Gets all tasks livedata old.
     *
     * @return the all tasks livedata old to get
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataOld() {
        return allTasksLivedataOld;
    }

    /**
     * Gets all tasks livedata recent.
     *
     * @return the all tasks livedata recent to get
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataRecent() {
        return allTasksLivedataRecent;
    }

    // setter

    /**
     * Sets do in background.
     *
     * @param doInBackground the do in background
     */
    public void setDoInBackground(Executor doInBackground) {
        this.doInBackground = doInBackground;
    }
}
