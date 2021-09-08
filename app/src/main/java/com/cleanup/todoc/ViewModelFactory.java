package com.cleanup.todoc;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.di.TodocApplication;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;
import com.cleanup.todoc.ui.MainViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;
    @NonNull
    private final ProjectRepository mProjectRepository;
    @NonNull
    private final TaskRepository mTaskRepository;

    private ViewModelFactory(
            @NonNull ProjectRepository projectRepository,
            @NonNull TaskRepository taskRepository
    ) {
        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
    }

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                factory = new ViewModelFactory(
                        TodocApplication.sTodocContainer.getProjectRepository(),
                        TodocApplication.sTodocContainer.getTaskRepository()
                );
            }
        }
        return factory;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class))
            return (T) new MainViewModel(mProjectRepository, mTaskRepository);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
