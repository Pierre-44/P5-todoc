package com.cleanup.todoc.ui;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.ui.UiTestModel.FIVE;
import static com.cleanup.todoc.ui.UiTestModel.FOUR;
import static com.cleanup.todoc.ui.UiTestModel.ONE;
import static com.cleanup.todoc.ui.UiTestModel.PROJECT1_TASK_1;
import static com.cleanup.todoc.ui.UiTestModel.PROJECT1_TASK_2;
import static com.cleanup.todoc.ui.UiTestModel.PROJECT1_TASK_3;
import static com.cleanup.todoc.ui.UiTestModel.PROJECT2_TASK_1;
import static com.cleanup.todoc.ui.UiTestModel.PROJECT2_TASK_2;
import static com.cleanup.todoc.ui.UiTestModel.PROJECT2_TASK_3;
import static com.cleanup.todoc.ui.UiTestModel.PROJECT_1;
import static com.cleanup.todoc.ui.UiTestModel.PROJECT_2;
import static com.cleanup.todoc.ui.UiTestModel.THREE;
import static com.cleanup.todoc.ui.UiTestModel.TWO;
import static com.cleanup.todoc.ui.UiTestModel.ZERO;
import static com.cleanup.todoc.utils.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    private ActivityScenario<MainActivity> activityScenario;

    //--------------------------------------------------
    // Test Rules , before and after
    //--------------------------------------------------


    @Before
    public void setUp() {
        activityScenario = ActivityScenario.launch(MainActivity.class);

    }

    @After
    public void tearDown() {
        activityScenario.close();
    }

    //--------------------------------------------------
    // MAIN ACTIVITY TEST
    //--------------------------------------------------

    @Test
    public void donTHaveTaskAndCheckNoTaskDisplayOnList() {
        // We check "no task" is displayed
        this.assertNoTaskVisibility();
        // We check the number of task
        this.assertTaskCount(ZERO);
    }


    @Test
    public void addTaskAndCheckIsDisplayOnList() {
        // We check "no task" is displayed
        this.assertNoTaskVisibility();

        // We add a new task
        this.addTask(PROJECT_1, PROJECT1_TASK_1);

        // We check "no task" gone and display now a task list
        this.assertAtLeastOneTaskIsVisible();

        // We check the number of task
        this.assertTaskCount(ONE);
    }

    @Test
    public void deleteTaskWithSuccess() {
        // We add a new task
        this.addTask(PROJECT_1, PROJECT1_TASK_1);

        // We perform click on delete button
        onView(withId(R.id.img_delete)).perform(click());

        // We check "no task" is displayed
        this.assertNoTaskVisibility();
    }

    @Test
    public void sortTasksByRecentWithSuccess() {
        // We check "no task" is displayed
        this.assertNoTaskVisibility();

        // We add all Project1 tasks
        this.addTask(PROJECT_1, PROJECT1_TASK_1);
        this.addTask(PROJECT_1, PROJECT1_TASK_2);
        this.addTask(PROJECT_2, PROJECT1_TASK_3);

        // We check the number of task is 3
        this.assertTaskCount(THREE);

        // sorting by old
        this.assertOldSorting();

        // We open sorting option in the menu
        onView(withId(R.id.action_filter)).perform(click());

        // We set oldest first by clicking on in the menu
        onView(withText(R.string.sort_oldest_first)).perform(click());

        // Then sorting must be old first


    }

    //--------------------------------------------------
    // END OF TASK DAO TEST
    //--------------------------------------------------

    //--------------------------------------------------
    // METHODS FOR TESTING
    //--------------------------------------------------

    private void addTask(int projectId, String taskName) {
        // We click on the add fab button
        onView(withId(R.id.fab_add_task)).perform(click());
        // We check dialog title and positive button name
        onView(withId(R.id.alertTitle)).check(matches(withText(R.string.add_task)));
        onView(withId(android.R.id.button1)).check(matches(withText(R.string.add)));
        // We add the task name
        onView(withId(R.id.txt_task_name)).perform(replaceText(taskName));
        // We select the project
        onView(withId(R.id.project_spinner)).perform(click());
        onData(anything())
                .atPosition(projectId)
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());
        // We validate task adding clicking dialog positive button
        onView(withId(android.R.id.button1)).perform(click());
    }

    private void assertNoTaskVisibility() {
        // Check that the "no task" is displayed
        onView(withId(R.id.lbl_no_task))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        // Check that the task list is not displayed
        onView(withId(R.id.list_tasks))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    private void assertAtLeastOneTaskIsVisible() {
        // Check that the "no task" is displayed
        onView(withId(R.id.lbl_no_task))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        // Check that the task list is not displayed
        onView(withId(R.id.list_tasks))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    private void assertTaskCount(int count) {
        activityScenario.onActivity(activity -> assertEquals(count, activity.getTaskAdapterCount()));
    }

    private void assertOldSorting() {
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(ZERO, R.id.lbl_task_name))
                .check(matches(withText(PROJECT1_TASK_3)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(ONE, R.id.lbl_task_name))
                .check(matches(withText(PROJECT1_TASK_2)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(TWO, R.id.lbl_task_name))
                .check(matches(withText(PROJECT1_TASK_1)));
    }

    private void assertNewSorting() {
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(ZERO, R.id.lbl_task_name))
                .check(matches(withText(PROJECT1_TASK_1)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(ONE, R.id.lbl_task_name))
                .check(matches(withText(PROJECT1_TASK_2)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(TWO, R.id.lbl_task_name))
                .check(matches(withText(PROJECT1_TASK_3)));
    }

    private void assertAZSorting() {
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(THREE, R.id.lbl_task_name))
                .check(matches(withText(PROJECT2_TASK_1)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(FOUR, R.id.lbl_task_name))
                .check(matches(withText(PROJECT2_TASK_2)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(FIVE, R.id.lbl_task_name))
                .check(matches(withText(PROJECT2_TASK_3)));
    }

    private void assertZASorting() {
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(THREE, R.id.lbl_task_name))
                .check(matches(withText(PROJECT2_TASK_3)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(FOUR, R.id.lbl_task_name))
                .check(matches(withText(PROJECT2_TASK_2)));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(FIVE, R.id.lbl_task_name))
                .check(matches(withText(PROJECT2_TASK_1)));
    }
}
