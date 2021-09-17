package com.cleanup.todoc.ui;

import static com.cleanup.todoc.ui.Utils.SortMethod.NONE;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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

    //Repository
    private final ProjectRepository mProjectRepository;
    private final TaskRepository mTaskRepository;
    private final Executor mExecutor;

    private final LiveData<List<Project>> allProjects;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataAZ;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataZA;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataOld;
    private final LiveData<List<RelationTaskWithProject>> allTasksLivedataRecent;
    // Sorting
    public MutableLiveData<SortMethod> mSortingTypeMutableLiveData = new MutableLiveData<>();
    public LiveData<List<RelationTaskWithProject>> mListTaskLiveData = new LiveData<List<RelationTaskWithProject>>() {};
    public MediatorLiveData<List<RelationTaskWithProject>> mSortedListMediatorLiveData = new MediatorLiveData<>();

    // fields
    private TaskDao taskDao;
    private ProjectDao projectDao;

    /**
     * Instantiates a new Main view model.
     *
     * @param taskRepository the task repository
     */
// Constructor
    public MainViewModel(ProjectRepository projectRepository, TaskRepository taskRepository, Executor executor) {

        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
        this.mExecutor = executor;

        allProjects = projectRepository.getAllProjects();
        mSortingTypeMutableLiveData.setValue(NONE);
        allTasksLivedataAZ = mTaskRepository.getAllTasksLivedataAZ();
        allTasksLivedataZA = mTaskRepository.getAllTasksLivedataZA();
        allTasksLivedataOld = mTaskRepository.getAllTasksLivedataOld();
        allTasksLivedataRecent = mTaskRepository.getAllTasksLivedataRecent();


        //Adding data as source to mediator
        mSortedListMediatorLiveData.addSource(mListTaskLiveData, new Observer<List<RelationTaskWithProject>>() {
            @Override
            public void onChanged(List<RelationTaskWithProject> relationTaskWithProjects) {
                mSortedListMediatorLiveData.setValue(combineDataAndSortingType(mListTaskLiveData, mSortingTypeMutableLiveData));

            }
        });

        //Adding order as source to mediator
        mSortedListMediatorLiveData.addSource(mSortingTypeMutableLiveData, new Observer<SortMethod>() {
            @Override
            public void onChanged(SortMethod sortingType) {
                mSortedListMediatorLiveData.setValue(combineDataAndSortingType(mListTaskLiveData, mSortingTypeMutableLiveData));
            }
        });
    }


    /**
     * Sort the task list depending on active sorting method and set related live data and
     * launch view state setting
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
                Collections.sort(listToSort, new Task.TaskAZComparator());
                return listToSort;
            case ALPHABETICAL_INVERTED:
                Collections.sort(listToSort, new Task.TaskZAComparator());
                return listToSort;
            case OLD_FIRST:
                Collections.sort(listToSort, new Task.TaskOldComparator());
                return listToSort;
            case RECENT_FIRST:
                Collections.sort(listToSort, new Task.TaskRecentComparator());
                return listToSort;
            default:
                return listToSort;
        }
    }

    public void deleteTaskById(final int id){

    }


    /**
     * Set the sorting method for the task list in the mutable live data and launch the sorting
     *
     * @param sortMethod the sorting method selected
     */
    public void setSorting(SortMethod sortMethod) {
        if (sortMethod != null) { // Useful when clicking on menu icon
            this.mSortingTypeMutableLiveData.setValue(sortMethod);
        }
    }
}
