package com.cleanup.todoc.model.repository;

import android.app.Application;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.entity.Project;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class ProjectRepository {

    //fields
    private final ProjectDao mProjectDao;
    private Executor doInBackground;


    private LiveData<List<Project>> allProjects;

    // Constructor
    /**
     * Instantiates a new Project repository.
     *
     * @param application the application
     */
    public ProjectRepository(Application application) {
        TodocDatabase todocDatabase = TodocDatabase.getInstance(application);
        mProjectDao = todocDatabase.mProjectDao();
        allProjects = mProjectDao.getAllProjects();
        doInBackground = Executors.newFixedThreadPool(3);
    }

    /**
     * Instantiates a new Project repository.
     *
     * @param projectDao the project dao
     */
    @VisibleForTesting
    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
        doInBackground = Executors.newFixedThreadPool(3);
    }

    /**
     * Insert.
     *
     * @param project the project to insert
     */
// methods of interface
    public void insert(Project project) {
        doInBackground.execute(()-> mProjectDao.insert(project));
    }

    /**
     * Delete.
     *
     * @param project the project to delete
     */
    public void delete(Project project) {
        doInBackground.execute(()-> mProjectDao.delete(project));
    }

    // getter

    /**
     * Gets all projects live data.
     *
     * @return the all projects live data
     */
    public LiveData<List<Project>> getAllProjectsLiveData() {
        return allProjects;
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

