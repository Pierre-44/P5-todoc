package com.cleanup.todoc.data;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.graphics.Color;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
import com.cleanup.todoc.model.entity.Task;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

/**
 * Created by pmeignen on 23/09/2021.
 */
@RunWith(AndroidJUnit4.class)
public class DbInstrumentedTest {

    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;

    private TodocDatabase mTodocDatabase;

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        mTaskDao = mTodocDatabase.mTaskDao();
        mProjectDao = mTodocDatabase.mProjectDao();
    }

    @Before
    public void addProject(){
        Project testProject = new Project("testProject", Color.GREEN);
        mProjectDao.insert(testProject);
    }

    @After
    public void closeDb() throws IOException {
        mTodocDatabase.close();
    }

    @Test
    public void getProjects() throws InterruptedException {
        List<Project> testProjectList = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        assertEquals("testProject",testProjectList.get(0).getProjectName());
    }

    @Test
    public void insertTaskInDb() throws InterruptedException {
        Task testTask0 = new Task(0,1,"testTask0",1630497600 );
        mTaskDao.insert(testTask0);
        List<RelationTaskWithProject> testTaskList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals("testTask0",testTaskList.get(0).getTask().getTaskName());
    }

    @Test
    public void getAllTasksInDb() throws InterruptedException {
        Task testTask1 = new Task(1,1,"testTask1",1630497601 );
        Task testTask2 = new Task(2,1,"testTask2",1630497602 );
        Task testTask3 = new Task(3,1,"testTask3",1630497603 );
        mTaskDao.insert(testTask1);
        mTaskDao.insert(testTask2);
        mTaskDao.insert(testTask3);
        List<RelationTaskWithProject> testTaskList = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals(3,testTaskList.size());
    }

    @Test
    public void deleteTaskInDb() throws InterruptedException {
        Task testTask4 = new Task(4, 1, "testTask4", 1630497604);
        mTaskDao.insert(testTask4);
        List<RelationTaskWithProject> testTaskList1 = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals("testTask4", testTaskList1.get(0).getTask().getTaskName());

        mTaskDao.delete(testTask4);
        List<RelationTaskWithProject> testTaskList2 = LiveDataTestUtils.getOrAwaitValue(mTaskDao.getAllTasks());
        assertEquals(0, testTaskList2.size());
    }
}

