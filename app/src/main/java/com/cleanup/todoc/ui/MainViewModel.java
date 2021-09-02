package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.Task;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;

import java.util.List;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class MainViewModel extends ViewModel {

    //fields
    private TaskDao taskDao;
    private ProjectDao projectDao;

    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;

    private final LiveData<List<Project>> allProjectsLivedata;
    private final LiveData<List<Task>> allTasksLivedata;
    private final LiveData<List<Task>> allTasksLivedataAZ;
    private final LiveData<List<Task>> allTasksLivedataZA;
    private final LiveData<List<Task>> allTasksLivedataOld;
    private final LiveData<List<Task>> allTasksLivedataRecent;

    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
        allProjectsLivedata = projectRepository.getAllProjects();
        allTasksLivedata = taskDao.getAllTasks();
        allTasksLivedataAZ = taskDao.getAllTasksByNameAZ();
        allTasksLivedataZA = taskDao.getAllTasksByNameZA();
        allTasksLivedataOld = taskDao.getAllTasksByTimeStampOld();
        allTasksLivedataRecent = taskDao.getAllTasksByTimeStampRecent();
    }

    public void insertTask(Task task) {
        mTaskRepository.insert(task);
    }

    public void updateTask(Task task) {
        mTaskRepository.update(task);
    }

    public void deleteTask(Task task) {
        mTaskRepository.delete(task);
    }

    public void insertProject(Project project) {
        mProjectRepository.insert(project);
    }

    public void deleteProject(Project project) {
        mProjectRepository.delete(project);
    }

    public LiveData<List<Project>> getAllProjectsLivedata() {
        return allProjectsLivedata;
    }

    public LiveData<List<Task>> getAllTasksLivedata() {
        return allTasksLivedata;
    }

    public LiveData<List<Task>> getAllTasksLivedataAZ() {
        return allTasksLivedataAZ;
    }

    public LiveData<List<Task>> getAllTasksLivedataZA() {
        return allTasksLivedataZA;
    }

    public LiveData<List<Task>> getAllTasksLivedataOld() {
        return allTasksLivedataOld;
    }

    public LiveData<List<Task>> getAllTasksLivedataRecent() {
        return allTasksLivedataRecent;
    }

    public ProjectRepository getProjectRepository() {
        return mProjectRepository;
    }

    public TaskRepository getTaskRepository() {
        return mTaskRepository;
    }
}
