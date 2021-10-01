package com.cleanup.todoc.data.dao;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.db.TodocDatabase;
import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
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
public class ProjectDaoTest {

    // Test fields
    private static final String EXPECTED_PROJECT_NAME_1 = "EXPECTED_PROJECT_NAME_1";
    private static final int EXPECTED_PROJECT_COLOR_1 = 1;
    private static final String EXPECTED_PROJECT_NAME_2 = "EXPECTED_PROJECT_NAME_2";
    private static final int EXPECTED_PROJECT_COLOR_2 = 2;

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
    public void closeDb() {
        mTodocDatabase.close();
    }

    @Test
    public void getAllProjectShouldReturnEmpty() throws InterruptedException {
        // When
        List<RelationTaskWithProject> projectList = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then
        assertTrue(projectList.isEmpty());
    }

    @Test
    public void insertOneProject() throws InterruptedException {
        // Given
        Project projectTest1 = new Project(EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);
        // When
        mProjectDao.insert(projectTest1);
        List<RelationTaskWithProject> projectList = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then
        assertSame(
                Collections.singletonList(
                        new Project(EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1)
                ),
                projectList
        );
    }

    @Test
    public void insertTwoProject() throws InterruptedException {
        // Given
        Project projectTest1 = new Project(EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);
        Project projectTest2 = new Project(EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2);
        // When
        mProjectDao.insert(projectTest1);
        mProjectDao.insert(projectTest2);
        List<RelationTaskWithProject> projectList = LiveDataTestUtils.getOrAwaitValue(mProjectDao.getAllProjects());
        // Then
        assertSame(
                Arrays.asList(
                        new Project(EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1),
                        new Project(EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2)
                ),
                projectList
        );
    }
}
