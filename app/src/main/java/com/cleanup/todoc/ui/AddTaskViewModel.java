package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
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

    private RelationTaskWithProject mRelationTaskWithProject;

    private LiveData<List<Project>> mProjects;


    public AddTaskViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;
        mExecutor = executor;

        mProjects = mProjectRepository.getAllProjects();

    }

    public LiveData<List<Project>> getProjects() {
        return mProjects;
    }

    // TODO : resolve insertion of new task not displayed
    public void insertTask(final Task task) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskRepository.insert(task);
            }
        });
    }
}
