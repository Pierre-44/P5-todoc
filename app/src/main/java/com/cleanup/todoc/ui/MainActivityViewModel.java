package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;

import java.util.List;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class MainActivityViewModel extends ViewModel {

    private final LiveData<List<Project>> projects;
    private final LiveData<List<Project>> tasksWithProject;
    private final LiveData<List<Project>> tasksWithsProjectSortAZ;


    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskrepository)

    public MainActivityViewModel() {
    }
}
