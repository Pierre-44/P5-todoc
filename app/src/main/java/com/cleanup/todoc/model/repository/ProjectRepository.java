package com.cleanup.todoc.model.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.entity.Project;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class ProjectRepository {

    //field
    private final ProjectDao mProjectDao;
    private final LiveData<List<Project>> allProjects;
    private final Executor doInBackground;

    // Constructor
    public ProjectRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
        allProjects = projectDao.getAllProjects();
        doInBackground = Executors.newFixedThreadPool(2);
    }

    public void insert(Project project) {
        doInBackground.execute(()-> mProjectDao.insert(project));
    }

    public void delete(Project project) {
        doInBackground.execute(()-> mProjectDao.delete(project));
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }
}

