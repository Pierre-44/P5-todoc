package com.cleanup.todoc.data.dao;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by pmeignen on 28/09/2021.
 */
@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // Test fields
    private static final long EXPECTED_TASK_ID_1 = 1;
    private static final long EXPECTED_PROJECT_ID_1 = 1;
    private static final String EXPECTED_TASK_NAME_1 = "EXPECTED_TASK_NAME_1";
    private static final int EXPECTED_TASK_TIMESTAMP_1 = 1630497601;
    private static final long EXPECTED_TASK_ID_2 = 2;
    private static final long EXPECTED_PROJECT_ID_2 = 2;
    private static final String EXPECTED_TASK_NAME_2 = "EXPECTED_TASK_NAME_2";
    private static final int EXPECTED_TASK_TIMESTAMP_2 = 1630497602;


    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private TodocDatabase mTodocDatabase;
    private TaskDao mTaskDao;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        mTaskDao = mTodocDatabase.mTaskDao();
        mTaskDao.deleteAllTasks();
    }

    @After
    public void closeDb() {
        mTodocDatabase.close();
    }

    @Test
    public void getAllTask_should_return_empty() throws InterruptedException {
        // When
        List<RelationTaskWithProject> taskList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        // Then
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void insert_one_task() throws InterruptedException {
        // Given
        Task taskTest1 = new Task(EXPECTED_TASK_ID_1,EXPECTED_PROJECT_ID_1,EXPECTED_TASK_NAME_1, EXPECTED_TASK_TIMESTAMP_1);
        // When
        mTaskDao.insert(taskTest1);
        List<RelationTaskWithProject> taskList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        // Then
        assertEquals(
                Collections.singletonList(
                        new Task(EXPECTED_TASK_ID_1,EXPECTED_PROJECT_ID_1,EXPECTED_TASK_NAME_1, EXPECTED_TASK_TIMESTAMP_1)
                ),
                taskList
        );
    }

    @Test
    public void insert_two_task() throws InterruptedException {
        // Given
        Task taskTest1 = new Task(EXPECTED_TASK_ID_1,EXPECTED_PROJECT_ID_1,EXPECTED_TASK_NAME_1, EXPECTED_TASK_TIMESTAMP_1);
        Task taskTest2 = new Task(EXPECTED_TASK_ID_2,EXPECTED_PROJECT_ID_2,EXPECTED_TASK_NAME_2, EXPECTED_TASK_TIMESTAMP_1);
        // When
        mTaskDao.insert(taskTest1);
        mTaskDao.insert(taskTest2);
        List<RelationTaskWithProject> taskList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        // Then
        assertEquals(
                Arrays.asList(
                        new Task(EXPECTED_TASK_ID_1, EXPECTED_PROJECT_ID_1, EXPECTED_TASK_NAME_1, EXPECTED_TASK_TIMESTAMP_1),
                        new Task(EXPECTED_TASK_ID_2, EXPECTED_PROJECT_ID_2, EXPECTED_TASK_NAME_2, EXPECTED_TASK_TIMESTAMP_1)
                ),
                taskList
        );
    }
}
