package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
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
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedata;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataAZ;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataZA;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataOld;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataRecent;

    /**
     * Instantiates a new Main view model.
     *
     * @param projectRepository the project repository
     * @param taskRepository    the task repository
     */
    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
        allProjectsLivedata = projectRepository.getAllProjects();
        allTasksLivedata = taskRepository.getAllTasksLivedata();
        allTasksLivedataAZ = taskRepository.getAllTasksLivedataAZ();
        allTasksLivedataZA = taskRepository.getAllTasksLivedataZA();
        allTasksLivedataOld = taskRepository.getAllTasksLivedataOld();
        allTasksLivedataRecent = taskRepository.getAllTasksLivedataRecent();
    }


    /**
     * Insert task in database.
     *
     * @param task the task to insert
     */
    public void insertTask(Task task) {
        mTaskRepository.insert(task);
    }

    /**
     * Update task in database.
     *
     * This feature is not implemented
     *
     * @param task the task to insert
     */
    public void updateTask(Task task) {
        //mTaskRepository.update(task);
    }

    /**
     * Delete task in database.
     *
     * @param task the task to insert
     */
    public void deleteTask(Task task) {
        mTaskRepository.deleteTask(task);
    }

    /**
     * Delete all task in database.
     *
     * This feature is not implemented
     *
     */
    public void deleteAllTask() {
        mTaskRepository.deleteAllTask();
    }

    /**
     * Insert project.
     *
     * This feature is not implemented
     *
     * @param project the project
     */
    public void insertProject(Project project) {
        mProjectRepository.insert(project);
    }

    /**
     * Delete project.
     *
     * This feature is not implemented
     *
     * @param project the project
     */
    public void deleteProject(Project project) {
        mProjectRepository.delete(project);
    }

    /**
     * Gets all projects they are in database on livedata
     *
     * @return the all projects on livedata
     */
    public LiveData<List<Project>> getAllProjectsLivedata() {
        return allProjectsLivedata;
    }

    /**
     * Gets all tasks livedata.
     *
     * @return the all tasks livedata
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedata() {
        return allTasksLivedata;
    }

    /**
     * Gets all tasks livedata az.
     *
     * @return the all tasks livedata az
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataAZ() {
        return allTasksLivedataAZ;
    }

    /**
     * Gets all tasks livedata za.
     *
     * @return the all tasks livedata za
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataZA() {
        return allTasksLivedataZA;
    }

    /**
     * Gets all tasks livedata old.
     *
     * @return the all tasks livedata old
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataOld() {
        return allTasksLivedataOld;
    }

    /**
     * Gets all tasks livedata recent.
     *
     * @return the all tasks livedata recent
     */
    public LiveData<List<RelationTaskWithProject>> getAllTasksLivedataRecent() {
        return allTasksLivedataRecent;
    }

    /**
     * Gets project repository.
     *
     * @return the project repository
     */
    public ProjectRepository getProjectRepository() {
        return mProjectRepository;
    }

    /**
     * Gets task repository.
     *
     * @return the task repository
     */
    public TaskRepository getTaskRepository() {
        return mTaskRepository;
    }
}
