package com.cleanup.todoc.ui;

import static org.junit.Assert.assertSame;

import android.graphics.Color;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
import com.cleanup.todoc.model.entity.Task;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
@RunWith(AndroidJUnit4.class)
public class TaskUnitTest {

    private static final Project testProject1 = new Project("TestProject", Color.BLUE);

    private static final Task testTask1 = new Task(1, 1, "testTask1", 1630497601);
    private static final Task testTask2 = new Task(1, 1, "testTask2", 1630497602);
    private static final Task testTask3 = new Task(1, 1, "testTask3", 1630497603);

    @Test
    public void test_az_comparator() {
        final RelationTaskWithProject task1 = new RelationTaskWithProject();
        final RelationTaskWithProject task2 = new RelationTaskWithProject();
        final RelationTaskWithProject task3 = new RelationTaskWithProject();

        final ArrayList<RelationTaskWithProject> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new RelationTaskWithProject.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }

    @Test
    public void test_za_comparator() {
        final RelationTaskWithProject task1 = new RelationTaskWithProject();
        final RelationTaskWithProject task2 = new RelationTaskWithProject();
        final RelationTaskWithProject task3 = new RelationTaskWithProject();

        final ArrayList<RelationTaskWithProject> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new RelationTaskWithProject.TaskZAComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final RelationTaskWithProject task1 = new RelationTaskWithProject();
        final RelationTaskWithProject task2 = new RelationTaskWithProject();
        final RelationTaskWithProject task3 = new RelationTaskWithProject();

        final ArrayList<RelationTaskWithProject> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new RelationTaskWithProject.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final RelationTaskWithProject task1 = new RelationTaskWithProject();
        final RelationTaskWithProject task2 = new RelationTaskWithProject();
        final RelationTaskWithProject task3 = new RelationTaskWithProject();

        final ArrayList<RelationTaskWithProject> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new RelationTaskWithProject.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}