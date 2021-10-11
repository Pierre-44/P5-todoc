package com.cleanup.todoc.data.dao;

import static com.cleanup.todoc.data.dao.DaoTestModel.EXPECTED_TASKS_COUNT_1;
import static com.cleanup.todoc.data.dao.DaoTestModel.EXPECTED_TASKS_COUNT_3;
import static com.cleanup.todoc.data.dao.DaoTestModel.EXPECTED_TASK_ID_1;
import static com.cleanup.todoc.data.dao.DaoTestModel.EXPECTED_TASK_ID_2;
import static com.cleanup.todoc.data.dao.DaoTestModel.EXPECTED_TASK_ID_3;
import static com.cleanup.todoc.data.dao.DaoTestModel.IN_FIRST_POSITION;
import static com.cleanup.todoc.data.dao.DaoTestModel.IN_SECOND_POSITION;
import static com.cleanup.todoc.data.dao.DaoTestModel.IN_THIRD_POSITION;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_1;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_2;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_3;
import static com.cleanup.todoc.data.dao.DaoTestModel.TASK_TEST_1;
import static com.cleanup.todoc.data.dao.DaoTestModel.TASK_TEST_2;
import static com.cleanup.todoc.data.dao.DaoTestModel.TASK_TEST_3;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
import com.cleanup.todoc.model.entity.Task;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pmeignen on 28/09/2021.
 */
@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    //--------------------------------------------------
    // Test Rules , before and after
    //--------------------------------------------------

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private TodocDatabase mTodocDatabase;
    private TaskDao mTaskDao;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        mTaskDao = mTodocDatabase.mTaskDao();
        // We add 3 test projects
        insertTestProjects();
        mTaskDao.deleteAllTasks();
    }

    @After
    public void closeAndClearDb() {
        mTodocDatabase.close();
        // mTodocDatabase.clearAllTables();
    }

    //--------------------------------------------------
    // TASK DAO TEST
    //--------------------------------------------------

    @Test
    public void getAllTaskShouldReturnEmpty() throws InterruptedException {
        // Given : no task and no project
        // When : we get the list of RelationTaskWithProject with getAllTasks()
        List<RelationTaskWithProject> relationTaskWithProjectList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        // Then : the list returned is empty
        assertTrue(relationTaskWithProjectList.isEmpty());
    }

    @Test
    public void getAllRelationTaskWithProjectWhenNoTaskInserted() throws InterruptedException {
        // Given : we add three projects in database and no task
        this.insertTestProjects();
        // When : we get the list of RelationTaskWithProject with getAllTasks()
        List<RelationTaskWithProject> relationTaskWithProjectList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        // Then : we call list and check she's empty
        assertTrue(relationTaskWithProjectList.isEmpty());
    }

    @Test
    public void insertOneTask() {
        // Given : insert Projects and add 1 task and set this id
        this.insertTestProjects();
        mTaskDao.insert(TASK_TEST_1);
        TASK_TEST_1.setTaskId(EXPECTED_TASK_ID_1);
        // When : we set a RelationTaskWithProject
        RelationTaskWithProject expectedRelationtaskwithproject1 = new RelationTaskWithProject();
        expectedRelationtaskwithproject1.setTask(TASK_TEST_1);
        expectedRelationtaskwithproject1.setProject(PROJECT_TEST_1);
        // Then : we check it's the correct task and project
        assertEquals(expectedRelationtaskwithproject1.getTask(), TASK_TEST_1);
        assertEquals(expectedRelationtaskwithproject1.getProject(), PROJECT_TEST_1);
    }

    @Test
    public void insertTwoTask() {
        // Given : insert Projects and add 2 tasks and set there ids
        this.insertTestProjects();
        mTaskDao.insert(TASK_TEST_1);
        TASK_TEST_1.setTaskId(EXPECTED_TASK_ID_1);
        mTaskDao.insert(TASK_TEST_2);
        TASK_TEST_2.setTaskId(EXPECTED_TASK_ID_2);
        // When : we set 2 RelationTaskWithProject
        RelationTaskWithProject expectedRelationtaskwithproject1 = new RelationTaskWithProject();
        expectedRelationtaskwithproject1.setTask(TASK_TEST_1);
        expectedRelationtaskwithproject1.setProject(PROJECT_TEST_1);

        RelationTaskWithProject expectedRelationtaskwithproject2 = new RelationTaskWithProject();
        expectedRelationtaskwithproject2.setTask(TASK_TEST_2);
        expectedRelationtaskwithproject2.setProject(PROJECT_TEST_2);

        // Then : we check it's the correct tasks and projects
        assertEquals(expectedRelationtaskwithproject1.getTask(), TASK_TEST_1);
        assertEquals(expectedRelationtaskwithproject1.getProject(), PROJECT_TEST_1);
        assertEquals(expectedRelationtaskwithproject2.getTask(), TASK_TEST_2);
        assertEquals(expectedRelationtaskwithproject2.getProject(), PROJECT_TEST_2);

    }

    @Test
    public void deleteOneTask() throws InterruptedException {
        // Given : insert Projects and add 1 tasks and set this id
        this.insertTestProjects();
        mTaskDao.insert(TASK_TEST_3);
        TASK_TEST_3.setTaskId(EXPECTED_TASK_ID_3);
        // When : we delete task with delete() and get the list of RelationTaskWithProject with getAllTasks()
        mTaskDao.delete(TASK_TEST_3);
        List<RelationTaskWithProject> relationTaskWithProjectList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        // Then :  check relationTaskWithProjectList is empty
        assertTrue(relationTaskWithProjectList.isEmpty());
    }

    @Test
    public void deleteOneOfTwoTaskById() throws InterruptedException {
        // Given : insert Projects and add 2 tasks and set there id
        mTaskDao.insert(TASK_TEST_2);
        TASK_TEST_2.setTaskId(EXPECTED_TASK_ID_3);
        mTaskDao.insert(TASK_TEST_3);
        TASK_TEST_3.setTaskId(EXPECTED_TASK_ID_3);
        // When : we delete task with deleteTaskById() (TASK_TEST_2)
        mTaskDao.deleteTaskById(EXPECTED_TASK_ID_2);
        // When : get the list of RelationTaskWithProject with getAllTasks()
        List<RelationTaskWithProject> relationTaskWithProjectList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals(EXPECTED_TASKS_COUNT_1, relationTaskWithProjectList.size());
        // When : check we have the correct task stayed (TASK_TEST_3)
        assertEquals(relationTaskWithProjectList.get(0).getTask(), TASK_TEST_3);
    }

    @Test
    public void insertAndGetAllRelationTaskWithProjectByNameAZ() throws InterruptedException {
        // Given : add 3 projects , add 3 tasks and set there tasks ids
        this.insertTestProjects();
        this.insertTestTasks();
        this.setTestTasksIds();
        // When : we get the list of RelationTaskWithProject with getAllTasksByNameAZ()
        List<RelationTaskWithProject> relationTaskWithProjectList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasksByNameAZ());
        // Then : we call list and check she's contains the 3 tasks , and check the order A -> Z
        assertEquals(EXPECTED_TASKS_COUNT_3, relationTaskWithProjectList.size());
        List<Task> actualTasks = getActualTaskInsertTest(relationTaskWithProjectList);

        // First task is the task with name start with A_
        assertEquals(TASK_TEST_1, actualTasks.get(IN_FIRST_POSITION));
        // Second task is the task with name start with B_
        assertEquals(TASK_TEST_2, actualTasks.get(IN_SECOND_POSITION));
        // Third task is the task with name start with C_
        assertEquals(TASK_TEST_3, actualTasks.get(IN_THIRD_POSITION));
    }

    @Test
    public void insertAndGetAllRelationTaskWithProjectByNameZA() throws InterruptedException {
        // Given : add 3 projects , add 3 tasks and set there tasks ids
        this.insertTestProjects();
        this.insertTestTasks();
        this.setTestTasksIds();
        // When : we get the list of RelationTaskWithProject with getAllTasksByNameZA()
        List<RelationTaskWithProject> relationTaskWithProjectList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasksByNameZA());
        // Then : we call list and check she's contains the 3 tasks , and check the order Z -> A
        assertEquals(EXPECTED_TASKS_COUNT_3, relationTaskWithProjectList.size());
        List<Task> actualTasks = getActualTaskInsertTest(relationTaskWithProjectList);

        // First task is the task with name start with C_
        assertEquals(TASK_TEST_3, actualTasks.get(IN_FIRST_POSITION));
        // Second task is the task with name start with B_
        assertEquals(TASK_TEST_2, actualTasks.get(IN_SECOND_POSITION));
        // Third task is the task with name start with A_
        assertEquals(TASK_TEST_1, actualTasks.get(IN_THIRD_POSITION));
    }

    @Test
    public void insertAndGetAllRelationTaskWithProjectByTimeStampOld() throws InterruptedException {
        // Given : add 3 projects , add 3 tasks and set there tasks ids
        this.insertTestProjects();
        this.insertTestTasks();
        this.setTestTasksIds();
        // When : we get the list of RelationTaskWithProject with getAllTasksByTimeStampOld()
        List<RelationTaskWithProject> relationTaskWithProjectList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasksByTimeStampOld());
        // Then : we call list and check she's contains the 3 tasks , and check the date order, the oldest in first
        assertEquals(EXPECTED_TASKS_COUNT_3, relationTaskWithProjectList.size());
        List<Task> actualTasks = getActualTaskInsertTest(relationTaskWithProjectList);

        // First task is the task with timestamp is oldest
        assertEquals(TASK_TEST_2, actualTasks.get(IN_FIRST_POSITION));
        // Second task is the task with timestamp is second oldest
        assertEquals(TASK_TEST_3, actualTasks.get(IN_SECOND_POSITION));
        // Third task is the task with timestamp is recentest
        assertEquals(TASK_TEST_1, actualTasks.get(IN_THIRD_POSITION));
    }

    @Test
    public void insertAndGetAllRelationTaskWithProjectByTimeStampRecent() throws InterruptedException {
        // Given : add 3 projects , add 3 tasks and set there tasks ids
        this.insertTestProjects();
        this.insertTestTasks();
        this.setTestTasksIds();
        // When : we get the list of RelationTaskWithProject with getAllTasksByTimeStampRecent()
        List<RelationTaskWithProject> relationTaskWithProjectList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasksByTimeStampRecent());
        // Then : we call list and check she's contains the 3 tasks , and check the date order, the recentest in first
        assertEquals(EXPECTED_TASKS_COUNT_3, relationTaskWithProjectList.size());
        List<Task> actualTasks = getActualTaskInsertTest(relationTaskWithProjectList);

        // First task is the task with timestamp is recentest
        assertEquals(TASK_TEST_1, actualTasks.get(IN_FIRST_POSITION));
        // Second task is the task with timestamp is second recentest
        assertEquals(TASK_TEST_3, actualTasks.get(IN_SECOND_POSITION));
        // Third task is the task with timestamp is oldest
        assertEquals(TASK_TEST_2, actualTasks.get(IN_THIRD_POSITION));
    }

    //--------------------------------------------------
    // END OF TASK DAO TEST
    //--------------------------------------------------

    //--------------------------------------------------
    // METHODS FOR TESTING
    //--------------------------------------------------
    private void insertTestProjects() {
        mTodocDatabase.mProjectDao().insert(PROJECT_TEST_1);
        mTodocDatabase.mProjectDao().insert(PROJECT_TEST_2);
        mTodocDatabase.mProjectDao().insert(PROJECT_TEST_3);
    }

    private void insertTestTasks() {
        mTodocDatabase.mTaskDao().insert(TASK_TEST_1);
        mTodocDatabase.mTaskDao().insert(TASK_TEST_2);
        mTodocDatabase.mTaskDao().insert(TASK_TEST_3);
    }

    private List<Task> getActualTaskInsertTest(List<RelationTaskWithProject> actualTaskInsertTest) {
        return Arrays.asList(
                actualTaskInsertTest.get(0).getTask(),
                actualTaskInsertTest.get(1).getTask(),
                actualTaskInsertTest.get(2).getTask()
        );
    }

    private void setTestTasksIds() {
        TASK_TEST_1.setTaskId(EXPECTED_TASK_ID_1);
        TASK_TEST_2.setTaskId(EXPECTED_TASK_ID_2);
        TASK_TEST_3.setTaskId(EXPECTED_TASK_ID_3);
    }
}
