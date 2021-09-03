package com.cleanup.todoc.di;

import android.app.Application;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;

/**
 * Created by pmeignen on 31/08/2021.
 */
public class TodocContainer {

    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;

    public TodocContainer(Application application) {
        TodocDatabase database = TodocDatabase.getInstance(application);
        mProjectRepository = new ProjectRepository(application);
        mTaskRepository = new TaskRepository(application);
    }

    public ProjectRepository getProjectRepository() {return mProjectRepository;}
    public TaskRepository getTaskRepository() {return mTaskRepository;}
}
