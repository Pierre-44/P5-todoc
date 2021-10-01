package com.cleanup.todoc.data;

import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verify;

import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Task;
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
public class TaskRepositoryTest {

    private static final Task TASK1_TEST = new Task(1, 1, "test_task1",0);
    private static final long TASK2_TEST = new Task(2,1,"test_task2",0).getTaskId() ;

    @Mock
    TaskDao mockTaskDao;

    // ProjectRepository to use on test
    private ProjectRepository mockProjectRepository;
    // TaskRepository to use on test
    private TaskRepository mockTaskRepository;
    // Executor to use on test
    private Executor testExecutor;

    @Before
    public void setTaskRepositoryTest(){
        mockTaskRepository = new TaskRepository(mockTaskDao);
        testExecutor = Executors.newFixedThreadPool(2);
        mockTaskRepository.setDoInBackground(testExecutor);
    }

    @Test
    public void verifyInsertCallsDaoInsert() {
        // When :
        mockTaskRepository.insert(TASK1_TEST);
        // Then :
        verify(mockTaskDao, after(100).times(1)).insert(TASK1_TEST);
    }

    @Test
    public void verifyDeleteCallsDaoDelete() {
        // When :
        mockTaskRepository.delete(TASK1_TEST);
        // Then :
        verify(mockTaskDao, after(100).times(1)).delete(TASK1_TEST);
    }

    @Test
    public void verifyDeleteTaskByIdCallsDaoDeleteTaskBy() {
        // When :
        mockTaskRepository.deleteTaskById(TASK2_TEST);
        // Then :
        verify(mockTaskDao, after(100).times(1)).deleteTaskById(TASK2_TEST);
    }
}

