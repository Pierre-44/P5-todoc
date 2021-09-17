package com.cleanup.todoc.model.repository;

import android.app.Application;

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
    private final Executor doInBackground;


    private final LiveData<List<Project>> allProjects;

    // Constructor
    public ProjectRepository(Application application) {
        TodocDatabase todocDatabase = TodocDatabase.getInstance(application);
        mProjectDao = todocDatabase.mProjectDao();
        allProjects = mProjectDao.getAllProjects();
        doInBackground = Executors.newFixedThreadPool(2);
    }

    // methods of interface
    public void insert(Project project) {
        doInBackground.execute(()-> mProjectDao.insert(project));
    }

    public void delete(Project project) {
        doInBackground.execute(()-> mProjectDao.delete(project));
    }

    // getter
    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

}

