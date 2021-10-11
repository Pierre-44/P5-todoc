package com.cleanup.todoc.data;

import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verify;

import android.graphics.Color;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.repository.ProjectRepository;
import com.cleanup.todoc.model.repository.TaskRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pmeignen on 01/10/2021.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectRepositoryTest {

    //--------------------------------------------------
    // Field , Test Rules , before and after
    //--------------------------------------------------

    private static final Project PROJECT1_TEST = new Project("test_project1", Color.GREEN);

    @Mock
    ProjectDao mockProjectDao;

    // ProjectRepository to use on test
    private ProjectRepository mockProjectRepository;
    // TaskRepository to use on test
    private TaskRepository mockTaskRepository;
    // Executor to use on test
    private Executor testExecutor;


    @Before
    public void setProjectRepositoryTest(){
        mockProjectRepository = new ProjectRepository(mockProjectDao);
        testExecutor = Executors.newFixedThreadPool(1);
        mockProjectRepository.setDoInBackground(testExecutor);
    }

    //--------------------------------------------------
    // PROJECT REPOSITORY TEST
    //--------------------------------------------------

    @Test
    public void verifyInsertCallsDaoInsert() {
        // When :
        mockProjectRepository.insert(PROJECT1_TEST);
        // Then :
        verify(mockProjectDao, after(100).times(1)).insert(PROJECT1_TEST);
    }

    @Test
    public void verifyDeleteCallsDaoDelete() {
        // When :
        mockProjectRepository.delete(PROJECT1_TEST);
        // Then :
        verify(mockProjectDao, after(100).times(1)).delete(PROJECT1_TEST);
    }

    //--------------------------------------------------
    // END OF PROJECT REPOSITORY TEST
    //--------------------------------------------------
}
