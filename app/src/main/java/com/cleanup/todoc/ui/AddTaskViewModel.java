package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.Task;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by pmeignen on 14/09/2021.
 */
public class AddTaskViewModel extends ViewModel {

    //Repository
    private ProjectRepository mProjectRepository;
    private TaskRepository mTaskRepository;
    private Executor mExecutor;

    private LiveData<List<Project>> mProjects;


    /**
     * Instantiates a new Add task view model.
     *
     * @param projectRepository the project repository
     * @param taskRepository    the task repository
     * @param executor          the executor
     */
    public AddTaskViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        mExecutor = executor;
        mProjects = mProjectRepository.getAllProjectsLiveData();

    }

    /**
     * Gets projects.
     *
     * @return the projects to get
     */
    public LiveData<List<Project>> getProjects() {
        return mProjects;
    }

    /**
     * Insert task.
     *
     * @param task the task to get
     */
    public void insertTask(final Task task){
        mExecutor.execute(() -> mTaskRepository.insert(task));
    }
}
