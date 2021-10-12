package com.cleanup.todoc.ui;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.graphics.Color;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
import com.cleanup.todoc.model.entity.Task;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    //--------------------------------------------------
    // Fields
    //--------------------------------------------------

    //MainViewModel LiveDatas from repo to be mocked
    private final MutableLiveData<List<Project>> mListOfProjectMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<RelationTaskWithProject>> mListOfRelationTaskWithProjectMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<RelationTaskWithProject>> mListOfRelationTaskWithProjectAZMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<RelationTaskWithProject>> mListOfRelationTaskWithProjectZAMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<RelationTaskWithProject>> mListOfRelationTaskWithProjectOldMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<RelationTaskWithProject>> mListOfRelationTaskWithProjectRecentMutableLiveData = new MutableLiveData<>();
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    //set Task  :
    Task testTask0 = new Task(0, 1, "I task 0", new Date().getTime());
    Task testTask1 = new Task(1, 1, "H task 1", new Date().getTime() + 1);
    Task testTask2 = new Task(2, 1, "G task 2", new Date().getTime() + 2);

    //--------------------------------------------------
    // Test Rules , before and after
    //--------------------------------------------------
    Task testTask3 = new Task(3, 2, "F task 3", new Date().getTime() + 3);
    Task testTask4 = new Task(4, 2, "E task 4", new Date().getTime() + 4);
    Task testTask5 = new Task(5, 2, "D task 5", new Date().getTime() + 5);

    //--------------------------------------------------
    // MAIN VIEW MODEL TEST
    //--------------------------------------------------
    Task testTask6 = new Task(6, 3, "C task 6", new Date().getTime() + 6);
    Task testTask7 = new Task(7, 3, "B task 7", new Date().getTime() + 7);
    Task testTask8 = new Task(8, 3, "A task 8", new Date().getTime() + 8);
    @Mock // ProjectRepository to use on test
    private ProjectRepository mockProjectRepository;
    @Mock // TaskRepository to use on test
    private TaskRepository mockTaskRepository;
    // ViewModel to use on test
    private MainViewModel underTestMainViewModel;
    // Executor to use on test
    private Executor testExecutor = MainViewModel.getExecutor();

    @Before
    public void setUp() {
        //Mock LiveDatas from repositories
        mocking_mListOfProjectMutableLiveData();
        mocking_mListOfRelationTaskWithProjectMutableLiveData();
        mocking_mListOfRelationTaskWithProjectAZMutableLiveData();
        mocking_mListOfRelationTaskWithProjectZAMutableLiveData();
        mocking_mListOfRelationTaskWithProjectOldMutableLiveData();
        mocking_mListOfRelationTaskWithProjectRecentMutableLiveData();

        //Instantiate MainViewModel for testing, passing mocked repositories
        underTestMainViewModel = new MainViewModel(mockProjectRepository, mockTaskRepository);

    }

    //--------------------------------------------------
    // END OF MAIN VIEW MODEL TEST
    //--------------------------------------------------

    //--------------------------------------------------
    // METHODS FOR TESTING
    //--------------------------------------------------

    //Mocking repositories LiveData

    @After
    public void tearDown() {
    }

    @Test
    public void test_insertProject() {
        // Given : set a Project to insert
        Project projectToInsert = Objects.requireNonNull(this.mListOfProjectMutableLiveData.getValue()).get(0);
        // When : insert Project
        underTestMainViewModel.insertProject(projectToInsert);
        // Then : check if repository is called 1 time when we insert the project
        verify(mockProjectRepository, times(1)).insert(projectToInsert);
    }

    @Test
    public void test_insert_task() {
        // Given : set a Task to insert
        Task taskToInsert = Objects.requireNonNull(this.mListOfRelationTaskWithProjectMutableLiveData.getValue()).get(0).getTask();
        // When : insert Task
        underTestMainViewModel.insertTask(taskToInsert);
        // Then : check if repository is called 1 time when we insert the task
        verify(mockTaskRepository, times(1)).insert(taskToInsert);
    }

    @Test
    public void test_deleteTask() {
        // Given : set a Task to delete
        Task taskToDelete = Objects.requireNonNull(this.mListOfRelationTaskWithProjectMutableLiveData.getValue()).get(0).getTask();
        // When : delete task
        underTestMainViewModel.deleteTask(taskToDelete);
        // Then : check if repository is called 1 time when we delete the task
        verify(mockTaskRepository, times(1)).delete(taskToDelete);
    }

    @Test
    public void test_deleteTaskById() {
        // Given : set a Task to delete with id
        long taskToDeleteById = Objects.requireNonNull(this.mListOfRelationTaskWithProjectMutableLiveData.getValue()).get(0).getTask().getProjectId();
        // When : delete task with id
        underTestMainViewModel.deleteTaskById(taskToDeleteById);
        // Then : check if repository is called 1 time when we delete the task by id
        verify(mockTaskRepository, times(1)).deleteTaskById(taskToDeleteById);
    }

    @Test
    public void test_az_comparator() {
        // Given : set list of task for test
        List<RelationTaskWithProject> allTaskWithProjects = getRelationTaskWithProjectsForTest();
        // When : sorted with task comparator
        Collections.sort(allTaskWithProjects, new RelationTaskWithProject.TaskAZComparator());
        // Then : check the order of task
        assertSame(allTaskWithProjects.get(0).getTask(), testTask8);
        assertSame(allTaskWithProjects.get(1).getTask(), testTask7);
        assertSame(allTaskWithProjects.get(2).getTask(), testTask6);

    }

    @Test
    public void test_za_comparator() {
        // Given : set list of task for test
        List<RelationTaskWithProject> allTaskWithProjects = getRelationTaskWithProjectsForTest();
        // When : sorted with task comparator
        Collections.sort(allTaskWithProjects, new RelationTaskWithProject.TaskZAComparator());
        // Then : check the order of task
        assertSame(allTaskWithProjects.get(0).getTask(), testTask0);
        assertSame(allTaskWithProjects.get(1).getTask(), testTask1);
        assertSame(allTaskWithProjects.get(2).getTask(), testTask2);
    }

    @Test
    public void test_recent_comparator() {
        // Given : set list of task for test
        List<RelationTaskWithProject> allTaskWithProjects = getRelationTaskWithProjectsForTest();
        // When : sorted with task comparator
        Collections.sort(allTaskWithProjects, new RelationTaskWithProject.TaskRecentComparator());
        // Then : check the order of task
        assertSame(allTaskWithProjects.get(3).getTask(), testTask5);
        assertSame(allTaskWithProjects.get(4).getTask(), testTask4);
        assertSame(allTaskWithProjects.get(5).getTask(), testTask3);
    }

    @Test
    public void test_old_comparator() {
        // Given : set list of task for test
        List<RelationTaskWithProject> allTaskWithProjects = getRelationTaskWithProjectsForTest();
        // When : sorted with task comparator
        Collections.sort(allTaskWithProjects, new RelationTaskWithProject.TaskOldComparator());
        // Then : check the order of task
        assertSame(allTaskWithProjects.get(3).getTask(), testTask3);
        assertSame(allTaskWithProjects.get(4).getTask(), testTask4);
        assertSame(allTaskWithProjects.get(5).getTask(), testTask5);
    }

    private void mocking_mListOfProjectMutableLiveData() {
        mListOfProjectMutableLiveData.setValue(getAllProjectsForTest());
        doReturn(mListOfProjectMutableLiveData).when(mockProjectRepository).getAllProjectsLiveData();
    }

    private void mocking_mListOfRelationTaskWithProjectMutableLiveData() {
        List<RelationTaskWithProject> allRelationTaskWithProjects = getRelationTaskWithProjectsForTest();

        mListOfRelationTaskWithProjectMutableLiveData.setValue(allRelationTaskWithProjects);
    }

    private void mocking_mListOfRelationTaskWithProjectAZMutableLiveData() {
        List<RelationTaskWithProject> relationTaskWithProjectsAZ = Arrays.asList(
                getRelationTaskWithProjectsForTest().get(0),
                getRelationTaskWithProjectsForTest().get(1),
                getRelationTaskWithProjectsForTest().get(2),
                getRelationTaskWithProjectsForTest().get(3),
                getRelationTaskWithProjectsForTest().get(4),
                getRelationTaskWithProjectsForTest().get(5),
                getRelationTaskWithProjectsForTest().get(6),
                getRelationTaskWithProjectsForTest().get(7),
                getRelationTaskWithProjectsForTest().get(8)
        );
        mListOfRelationTaskWithProjectAZMutableLiveData.setValue(relationTaskWithProjectsAZ);
        doReturn(mListOfRelationTaskWithProjectAZMutableLiveData).when(mockTaskRepository).getAllTasksLivedataAZ();
    }

    private void mocking_mListOfRelationTaskWithProjectZAMutableLiveData() {
        List<RelationTaskWithProject> relationTaskWithProjectsZA = Arrays.asList(
                getRelationTaskWithProjectsForTest().get(8),
                getRelationTaskWithProjectsForTest().get(7),
                getRelationTaskWithProjectsForTest().get(6),
                getRelationTaskWithProjectsForTest().get(5),
                getRelationTaskWithProjectsForTest().get(4),
                getRelationTaskWithProjectsForTest().get(3),
                getRelationTaskWithProjectsForTest().get(2),
                getRelationTaskWithProjectsForTest().get(1),
                getRelationTaskWithProjectsForTest().get(0)
        );
        mListOfRelationTaskWithProjectZAMutableLiveData.setValue(relationTaskWithProjectsZA);
        doReturn(mListOfRelationTaskWithProjectZAMutableLiveData).when(mockTaskRepository).getAllTasksLivedataZA();
    }

    private void mocking_mListOfRelationTaskWithProjectOldMutableLiveData() {
        List<RelationTaskWithProject> relationTaskWithProjectsOld = Arrays.asList(
                getRelationTaskWithProjectsForTest().get(0),
                getRelationTaskWithProjectsForTest().get(1),
                getRelationTaskWithProjectsForTest().get(2),
                getRelationTaskWithProjectsForTest().get(3),
                getRelationTaskWithProjectsForTest().get(4),
                getRelationTaskWithProjectsForTest().get(5),
                getRelationTaskWithProjectsForTest().get(6),
                getRelationTaskWithProjectsForTest().get(7),
                getRelationTaskWithProjectsForTest().get(8)
        );
        mListOfRelationTaskWithProjectOldMutableLiveData.setValue(relationTaskWithProjectsOld);
        doReturn(mListOfRelationTaskWithProjectOldMutableLiveData).when(mockTaskRepository).getAllTasksLivedataOld();
    }

    private void mocking_mListOfRelationTaskWithProjectRecentMutableLiveData() {
        List<RelationTaskWithProject> relationTaskWithProjectsRecent = Arrays.asList(
                getRelationTaskWithProjectsForTest().get(8),
                getRelationTaskWithProjectsForTest().get(7),
                getRelationTaskWithProjectsForTest().get(6),
                getRelationTaskWithProjectsForTest().get(5),
                getRelationTaskWithProjectsForTest().get(4),
                getRelationTaskWithProjectsForTest().get(3),
                getRelationTaskWithProjectsForTest().get(2),
                getRelationTaskWithProjectsForTest().get(1),
                getRelationTaskWithProjectsForTest().get(0)
        );
        mListOfRelationTaskWithProjectRecentMutableLiveData.setValue(relationTaskWithProjectsRecent);
        doReturn(mListOfRelationTaskWithProjectRecentMutableLiveData).when(mockTaskRepository).getAllTasksLivedataRecent();
    }

    private List<RelationTaskWithProject> getRelationTaskWithProjectsForTest() {

        //relationTaskWithProject0
        RelationTaskWithProject relationTaskWithProject0 = new RelationTaskWithProject();
        relationTaskWithProject0.setProject(getAllProjectsForTest().get(0));
        relationTaskWithProject0.setTask(testTask0);
        //relationTaskWithProject1
        RelationTaskWithProject relationTaskWithProject1 = new RelationTaskWithProject();
        relationTaskWithProject1.setProject(getAllProjectsForTest().get(0));
        relationTaskWithProject1.setTask(testTask1);
        //relationTaskWithProject2
        RelationTaskWithProject relationTaskWithProject2 = new RelationTaskWithProject();
        relationTaskWithProject2.setProject(getAllProjectsForTest().get(0));
        relationTaskWithProject2.setTask(testTask2);
        //relationTaskWithProject3
        RelationTaskWithProject relationTaskWithProject3 = new RelationTaskWithProject();
        relationTaskWithProject3.setProject(getAllProjectsForTest().get(1));
        relationTaskWithProject3.setTask(testTask3);
        //relationTaskWithProject4
        RelationTaskWithProject relationTaskWithProject4 = new RelationTaskWithProject();
        relationTaskWithProject4.setProject(getAllProjectsForTest().get(1));
        relationTaskWithProject4.setTask(testTask4);
        //relationTaskWithProject5
        RelationTaskWithProject relationTaskWithProject5 = new RelationTaskWithProject();
        relationTaskWithProject5.setProject(getAllProjectsForTest().get(1));
        relationTaskWithProject5.setTask(testTask5);
        //relationTaskWithProject6
        RelationTaskWithProject relationTaskWithProject6 = new RelationTaskWithProject();
        relationTaskWithProject6.setProject(getAllProjectsForTest().get(2));
        relationTaskWithProject6.setTask(testTask6);
        //relationTaskWithProject7
        RelationTaskWithProject relationTaskWithProject7 = new RelationTaskWithProject();
        relationTaskWithProject7.setProject(getAllProjectsForTest().get(2));
        relationTaskWithProject7.setTask(testTask7);
        //relationTaskWithProject8
        RelationTaskWithProject relationTaskWithProject8 = new RelationTaskWithProject();
        relationTaskWithProject8.setProject(getAllProjectsForTest().get(2));
        relationTaskWithProject8.setTask(testTask8);

        // return Arrays as list
        return Arrays.asList(
                relationTaskWithProject0,
                relationTaskWithProject1,
                relationTaskWithProject2,
                relationTaskWithProject3,
                relationTaskWithProject4,
                relationTaskWithProject5,
                relationTaskWithProject6,
                relationTaskWithProject7,
                relationTaskWithProject8
        );
    }

    // Setting Project Tasks and RelationTaskWithProject
    private List<Project> getAllProjectsForTest() {
        List<Project> allProject = Arrays.asList(
                new Project("Test Project 1", Color.BLUE),
                new Project("Test Project 1", Color.BLUE),
                new Project("Test Project 1", Color.BLUE)
        );

        // set id instated of autogenerated by Room
        allProject.get(0).setProjectId(1);
        allProject.get(1).setProjectId(2);
        allProject.get(2).setProjectId(3);

        return allProject;
    }
}