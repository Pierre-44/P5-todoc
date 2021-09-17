package com.cleanup.todoc.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;
import com.cleanup.todoc.ui.AddTaskViewModel;
import com.cleanup.todoc.ui.MainViewModel;

import java.util.concurrent.Executor;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;
    private final Executor mExecutor;

    private ViewModelFactory(@NonNull ProjectRepository projectRepository, @NonNull TaskRepository taskRepository, Executor executor) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        mExecutor = executor;
    }

    public static ViewModelFactory getInstance(@NonNull ProjectRepository projectRepository, @NonNull TaskRepository taskRepository, Executor executor){

        if (factory == null){
            factory = new ViewModelFactory(projectRepository,taskRepository, executor);
        }
        return factory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(projectRepository,taskRepository, mExecutor);
        } else if (modelClass.isAssignableFrom(AddTaskViewModel.class)){
            return (T) new AddTaskViewModel(projectRepository,taskRepository, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
