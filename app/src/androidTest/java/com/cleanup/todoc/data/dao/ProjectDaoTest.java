package com.cleanup.todoc.data.dao;

import static com.cleanup.todoc.data.dao.DaoTestModel.EXPECTED_PROJECTS_COUNT_2;
import static com.cleanup.todoc.data.dao.DaoTestModel.EXPECTED_PROJECTS_COUNT_3;
import static com.cleanup.todoc.data.dao.DaoTestModel.IN_FIRST_POSITION;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_1;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_1_ID;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_2;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_2_ID;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_3;
import static com.cleanup.todoc.data.dao.DaoTestModel.PROJECT_TEST_3_ID;
import static org.junit.Assert.assertArrayEquals;
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
        // Then : check projectList is empty
        assertTrue(projectList.isEmpty());
    }

    //--------------------------------------------------
    // PROJECT DAO TEST
    //--------------------------------------------------

    @Test
    public void insertAllProject() throws InterruptedException {
        // Given : insert 2 Projects and set there ids
        this.insertTestProjects();
        this.setProjectIds();
        List<Project> expectedProjectsList = Arrays.asList(
                PROJECT_TEST_1,PROJECT_TEST_2,PROJECT_TEST_3);
        // When : get the list of RelationTaskWithProject with getAllProjects()
        List<Project> actualProjects = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then : check projectList contain 3 projects
        assertEquals(EXPECTED_PROJECTS_COUNT_3, actualProjects.size());
        // Then : check we have the correct project
        assertEquals(expectedProjectsList,actualProjects);
    }

    @Test
    public void insertAllProjectAndDeleteOne() throws InterruptedException {
        // Given : insert 2 Projects and set there ids
        this.insertTestProjects();
        this.setProjectIds();
        // When : delete the project in first position
        Project projectToDelete =
                LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects()).get(IN_FIRST_POSITION);
        mProjectDao.delete(projectToDelete);

        // When : get the list of Project with getAllProjects()
        List<Project> expectedProjectsList = Arrays.asList(
                PROJECT_TEST_2,PROJECT_TEST_3);
        List<Project> actualProjects = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then : check projectList contain 2 projects
        assertEquals(EXPECTED_PROJECTS_COUNT_2, actualProjects.size());
        // Then : check the 2 projected stayed
        assertArrayEquals(expectedProjectsList.toArray(), actualProjects.toArray());
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

    private void setProjectIds() {
        PROJECT_TEST_1.setProjectId(PROJECT_TEST_1_ID);
        PROJECT_TEST_2.setProjectId(PROJECT_TEST_2_ID);
        PROJECT_TEST_3.setProjectId(PROJECT_TEST_3_ID);
    }
}
