package com.cleanup.todoc.data.dao;

import android.graphics.Color;

import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.Task;

/**
 * Created by pmeignen on 07/10/2021.
 */
public class DaoTestModel {

    //--------------------------------------------------
    // Test fields and constants for dao tests
    //--------------------------------------------------

    // PROJECT_TEST_1
    public static final int PROJECT_TEST_1_ID = 1;
    public static final String PROJECT_TEST_1_NAME = "PROJECT_TEST_1";
    public static final int PROJECT_TEST_1_COLOR = Color.BLUE;
    public static final Project PROJECT_TEST_1 = new Project(PROJECT_TEST_1_NAME,PROJECT_TEST_1_COLOR);

    // PROJECT_TEST_2
    public static final int PROJECT_TEST_2_ID = 2;
    public static final String PROJECT_TEST_2_NAME = "PROJECT_TEST_2";
    public static final int PROJECT_TEST_2_COLOR = Color.GREEN;
    public static final Project PROJECT_TEST_2 = new Project(PROJECT_TEST_2_NAME,PROJECT_TEST_2_COLOR);

    // PROJECT_TEST_3
    public static final int PROJECT_TEST_3_ID = 3;
    private static final String PROJECT_TEST_3_NAME = "PROJECT_TEST_3";
    private static final int PROJECT_TEST_3_COLOR = Color.RED;
    public static final Project PROJECT_TEST_3 = new Project(PROJECT_TEST_3_NAME,PROJECT_TEST_3_COLOR);

    // EXPECTED_TASK_ID_1
    public static final long EXPECTED_TASK_ID_1 = 1;
    public static final String EXPECTED_TASK_NAME_1 = "A_EXPECTED_TASK_NAME_1";
    public static final int EXPECTED_TASK_TIMESTAMP_1 = 1609455601;
    public static final Task TASK_TEST_1 = new Task(EXPECTED_TASK_ID_1,PROJECT_TEST_1_ID,EXPECTED_TASK_NAME_1,EXPECTED_TASK_TIMESTAMP_1) ;

    // EXPECTED_TASK_ID_2
    public static final long EXPECTED_TASK_ID_2 = 2;
    public static final String EXPECTED_TASK_NAME_2 = "B_EXPECTED_TASK_NAME_2";
    public static final int EXPECTED_TASK_TIMESTAMP_2 = 1609455603;
    public static final Task TASK_TEST_2 = new Task(EXPECTED_TASK_ID_2,PROJECT_TEST_2_ID,EXPECTED_TASK_NAME_2,EXPECTED_TASK_TIMESTAMP_2) ;

    // EXPECTED_TASK_ID_2
    public static final long EXPECTED_TASK_ID_3 = 3;
    private static final String EXPECTED_TASK_NAME_3 = "C_EXPECTED_TASK_NAME_3";
    private static final int EXPECTED_TASK_TIMESTAMP_3 = 1609455602;
    public static final Task TASK_TEST_3 = new Task(EXPECTED_TASK_ID_3,PROJECT_TEST_3_ID,EXPECTED_TASK_NAME_3,EXPECTED_TASK_TIMESTAMP_3) ;

    // EXPECTED_TASKS_COUNT
    public static final int EXPECTED_TASKS_COUNT_3 = 3 ;
    public static final int EXPECTED_TASKS_COUNT_1 = 1 ;

    // EXPECTED_PROJECTS_COUNT
    public static final int EXPECTED_PROJECTS_COUNT_3 = 3 ;
    public static final int EXPECTED_PROJECTS_COUNT_2 = 2 ;

    // INDEX POSITION
    public static final int IN_FIRST_POSITION = 0;
    public static final int IN_SECOND_POSITION = 1;
    public static final int IN_THIRD_POSITION = 2;

}
