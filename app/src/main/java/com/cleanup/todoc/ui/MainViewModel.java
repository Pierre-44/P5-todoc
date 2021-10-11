package com.cleanup.todoc.ui;

import static com.cleanup.todoc.ui.Utils.SortMethod.NONE;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
import com.cleanup.todoc.model.entity.Task;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;
import com.cleanup.todoc.ui.Utils.SortMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class MainViewModel extends ViewModel {

    //Executor
    private static Executor mExecutor;
    //Repository
    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    // Live Data
    private final LiveData<List<Project>> allProjects;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataAZ;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataZA;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataOld;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataRecent;

    // Sorting
    /**
     * The sorting type mutable live data.
     */
    public MutableLiveData<SortMethod> mSortingTypeMutableLiveData = new MutableLiveData<>();
    /**
     * The list task live data.
     */
    public LiveData<List<RelationTaskWithProject>> mListTaskLiveData;
    /**
     * The sorted list mediator live data.
     */
    public MediatorLiveData<List<RelationTaskWithProject>> mSortedListMediatorLiveData = new MediatorLiveData<>();

    // fields
    private TaskDao taskDao;
    private ProjectDao projectDao;

    // Constructor
    /**
     * Instantiates a new Main view model.
     *
     * @param projectRepository the project repository
     * @param taskRepository    the task repository
     * @param executor          the executor
     */
    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {

        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
        mExecutor = executor;

        allProjects = projectRepository.getAllProjectsLiveData();
        mSortingTypeMutableLiveData.setValue(NONE);
        allTasksLivedataAZ = mTaskRepository.getAllTasksLivedataAZ();
        allTasksLivedataZA = mTaskRepository.getAllTasksLivedataZA();
        allTasksLivedataOld = mTaskRepository.getAllTasksLivedataOld();
        allTasksLivedataRecent = mTaskRepository.getAllTasksLivedataRecent();

        //Adding data as source to mediator
        mListTaskLiveData = mTaskRepository.getAllRelationTaskWithProjectLiveData();
        mSortedListMediatorLiveData.addSource(mListTaskLiveData,
                relationTaskWithProjects -> mSortedListMediatorLiveData
                        .setValue(combineDataAndSortingType(mListTaskLiveData, mSortingTypeMutableLiveData)));

        //Adding order as source to mediator
        mSortedListMediatorLiveData.addSource(mSortingTypeMutableLiveData,
                sortingType -> mSortedListMediatorLiveData
                        .setValue(combineDataAndSortingType(mListTaskLiveData, mSortingTypeMutableLiveData)));
    }

    /**
     * Instantiates a new Main view model.
     *
     * @param projectRepository the project repository
     * @param taskRepository    the task repository
     */
    @VisibleForTesting
    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository) {
        mProjectRepository = projectRepository;
        mTaskRepository = taskRepository;

        allProjects = projectRepository.getAllProjectsLiveData();
        mSortingTypeMutableLiveData.setValue(NONE);
        allTasksLivedataAZ = mTaskRepository.getAllTasksLivedataAZ();
        allTasksLivedataZA = mTaskRepository.getAllTasksLivedataZA();
        allTasksLivedataOld = mTaskRepository.getAllTasksLivedataOld();
        allTasksLivedataRecent = mTaskRepository.getAllTasksLivedataRecent();
    }

    /**
     * Gets executor.
     *
     * @return the executor
     */
    public static Executor getExecutor() {
        return mExecutor;
    }

    /**
     * Sort the task list depending on active sorting method and set related live data
     *
     * @return sorted list with combine data ans sorting type
     */
    private List<RelationTaskWithProject> combineDataAndSortingType(LiveData<List<RelationTaskWithProject>> tasksLiveData, MutableLiveData<SortMethod> sortingTypeLiveData) {

        if (tasksLiveData.getValue() == null || sortingTypeLiveData.getValue() == null) {
            return new ArrayList<>();
        }

        List<RelationTaskWithProject> listToSort = tasksLiveData.getValue();

        switch (sortingTypeLiveData.getValue()) {
            case NONE:
                return tasksLiveData.getValue();
            case ALPHABETICAL:
                Collections.sort(listToSort, new RelationTaskWithProject.TaskAZComparator());
                return listToSort;
            case ALPHABETICAL_INVERTED:
                Collections.sort(listToSort, new RelationTaskWithProject.TaskZAComparator());
                return listToSort;
            case OLD_FIRST:
                Collections.sort(listToSort, new RelationTaskWithProject.TaskOldComparator());
                return listToSort;
            case RECENT_FIRST:
                Collections.sort(listToSort, new RelationTaskWithProject.TaskRecentComparator());
                return listToSort;
            default:
                return listToSort;
        }
    }

    /**
     * Delete task.
     *
     * @param task the task to deleted
     */
    public void deleteTask(Task task) {
        mTaskRepository.delete(task);
    }

    /**
     * Delete task by id.
     *
     * @param id the id of task to delete
     */
    public void deleteTaskById(final long id) {
        mTaskRepository.deleteTaskById(id);
    }

    /**
     * Insert project.
     *
     * @param project the project to insert
     */
    public void insertProject(Project project) {
        mProjectRepository.insert(project);
    }

    /**
     * Insert task.
     *
     * @param task the task to insert
     */
    public void insertTask(Task task) {
        mTaskRepository.insert(task);
    }

    /**
     * Set the sorting method for the task list in the mutable live data and launch the sorting
     *
     * @param sortMethod the sorting method selected
     */
    public void setSorting(SortMethod sortMethod) {
        if (sortMethod != null) {
            // Useful when clicking on menu icon
            this.mSortingTypeMutableLiveData.setValue(sortMethod);
        }
    }
}
