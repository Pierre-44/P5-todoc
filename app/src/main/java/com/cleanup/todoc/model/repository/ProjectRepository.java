package com.cleanup.todoc.model.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.dao.ProjectDao;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class ProjectRepository {

    //field
    private final LiveData<List<Project>> Projects;
    private final ProjectDao mProjectDao;
    private final Executor doInBackground;

    // Constructor
    public ProjectRepository(ProjectDao projectDao) {
        Projects = mProjectDao.getProject();
        mProjectDao = projectDao;
        doInBackground = null; // TODO : complete this
    }

    public void insert(Project project) {
        doInBackground.execute(() -> {
            if (mProjectDao != null) {
                mProjectDao.insert(project);
            }
        });
    }

    public void delete(Project project) {
        doInBackground.execute(() -> {
            if (mProjectDao != null) {
                mProjectDao.delete(project);
            }
        });
    }

    public LiveData<List<Project>> getProjects() {
        return Projects;
    }
}
