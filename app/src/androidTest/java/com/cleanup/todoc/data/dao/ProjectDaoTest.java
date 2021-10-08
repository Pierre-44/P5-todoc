package com.cleanup.todoc.data.dao;

import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_1;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_1_COLOR;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_1_ID;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_1_NAME;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_2;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_2_COLOR;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_2_ID;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_2_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pmeignen on 28/09/2021.
 */
@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {


    //--------------------------------------------------
    // Test Rules , before and after
    //--------------------------------------------------

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private TodocDatabase mTodocDatabase;
    private ProjectDao mProjectDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        mProjectDao = mTodocDatabase.mProjectDao();
        mProjectDao.deleteAllProjects();
    }

    @After
    public void closeAndClearDb() {
        mTodocDatabase.close();
        mTodocDatabase.clearAllTables();
    }

    @Test
    public void getAllProjectWhenNoProjectInsertedShouldReturnEmpty() throws InterruptedException {
        //Given : no project inserted in database
        // When : we get the list of project with getAllProjects()
        List<Project> projectList = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then
        assertTrue(projectList.isEmpty());
    }

    //--------------------------------------------------
    // PROJECT DAO TEST
    //--------------------------------------------------


    @Test
    public void insertOneProject() throws InterruptedException {
        // Given : insert 1 Project and set this id
        mProjectDao.insert(PROJECT_TEST_1);
        PROJECT_TEST_1.setProjectId(PROJECT_TEST_1_ID);
        // When : we set a Project
        Project EXPECTED_PROJECT_1 = new Project(PROJECT_TEST_1_NAME,PROJECT_TEST_1_COLOR);
        // Then : we check it's the correct project
        assertEquals(EXPECTED_PROJECT_1, PROJECT_TEST_1);

    }

    @Test
    public void insertTwoProject() throws InterruptedException {
        // Given : insert 2 Projects and set there ids
        mProjectDao.insert(PROJECT_TEST_1);
        PROJECT_TEST_1.setProjectId(PROJECT_TEST_1_ID);
        mProjectDao.insert(PROJECT_TEST_2);
        PROJECT_TEST_2.setProjectId(PROJECT_TEST_2_ID);
        // When : get the list of RelationTaskWithProject with getAllProjects()
        List<Project> projectList = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then : check we have the 2 correct project
        assertEquals(
                Arrays.asList(
                        new Project(PROJECT_TEST_1_NAME, PROJECT_TEST_1_COLOR),
                        new Project(PROJECT_TEST_2_NAME, PROJECT_TEST_2_COLOR)
                ),
                projectList
        );
    }

    @Test
    public void insertAllProjectAndDeleteOne() throws InterruptedException {
        // Given : insert 2 Projects and set there ids
        this.insertTestProjects();
        this.setProjectIds();
        // When : get the list of RelationTaskWithProject with getAllProjects()
        List<Project> projectList = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then : delete a project and check the last one
        mProjectDao.delete(PROJECT_TEST_1);
        assertEquals(projectList.get(0), PROJECT_TEST_2);
    }

    @Test
    public void insertAllProjectAndDeleteAll() throws InterruptedException {
        // Given : insert 2 Projects and set there ids
        this.insertTestProjects();
        this.setProjectIds();
        // When : get the list of RelationTaskWithProject with getAllProjects()
        List<Project> projectList = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then : delete all project
        mProjectDao.deleteAllProjects();
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
    }

    private void setProjectIds() {
        PROJECT_TEST_1.setProjectId(PROJECT_TEST_1_ID);
        PROJECT_TEST_2.setProjectId(PROJECT_TEST_2_ID);
    }
}
