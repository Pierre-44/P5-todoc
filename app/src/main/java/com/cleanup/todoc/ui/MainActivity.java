package com.cleanup.todoc.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.ActivityMainBinding;
import com.cleanup.todoc.di.TodocContainer;
import com.cleanup.todoc.di.ViewModelFactory;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;
import com.cleanup.todoc.model.entity.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executors;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    // fields

    /**
     * List of all projects available in the application
     */
    private Project[] allProjects ;

    /**
     * The adapter which handles the list of tasks
     */
    private final TasksAdapter adapter = new TasksAdapter(this);
    /**
     * Dialog to create a new task
     */
    @Nullable
    public AlertDialog dialog = null;
    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private Utils.SortMethod mSortingTypes;
    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;

    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;
    /**
     * The RecyclerView which displays the list of tasks
     */
    @NonNull
    private RecyclerView listTasks;

    /**
     * The TextView displaying the empty state
     */

    @NonNull
    private TextView lblNoTasks;
    /**
     * Floating action button to add a new task
     */
    @Nullable
    private FloatingActionButton fabAddTask;

    @Nullable
    private RelationTaskWithProject relationTaskWithProject;

    private ActivityMainBinding binding;
    private MainViewModel viewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewBinding init
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    /**
     * Init widgets & listeners
     */
    public void init() {

        TodocContainer container = new TodocContainer(getApplication());

        // RecyclerView for the list of task
        listTasks = binding.listTasks  ;
        binding.listTasks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        listTasks.setAdapter(adapter);
        listTasks.setHasFixedSize(true);
        // Onclick Listner on FAB to add task
        binding.fabAddTask.setOnClickListener(view -> showAddTaskAlertDialog());

        //viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(
                container.getProjectRepository(),
                container.getTaskRepository(),
                Executors.newSingleThreadExecutor()))
                .get(MainViewModel.class);

        // RecyclerView for the list of task
        viewModel.mSortedListMediatorLiveData.observe(this, relationTaskWithProjects -> {
            if (relationTaskWithProjects == null || relationTaskWithProjects.size() == 0) {
                binding.lblNoTask.setVisibility(View.VISIBLE);
                listTasks.setVisibility(View.GONE);
            } else {
                binding.lblNoTask.setVisibility(View.GONE);
                listTasks.setVisibility(View.VISIBLE);
                adapter.updateTasks(relationTaskWithProjects);
            }
        });
    }

    // Menu configuration
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // The sort method to be used to display tasks
        Utils.SortMethod sortMethod = null;

        if (id == R.id.filter_alphabetical) {
            sortMethod = Utils.SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = Utils.SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = Utils.SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = Utils.SortMethod.RECENT_FIRST;
        }
        // Sort method is set in VM which takes care of sorting the list
        viewModel.setSorting(sortMethod);

        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskAlertDialog() {

        FragmentManager fm = getSupportFragmentManager();
        AddTaskDialogFragment dialog = AddTaskDialogFragment.newInstance();
        dialog.show(fm, "add task");
    }

    public void insertTask(Task task) {
        viewModel.insertTask(task);
    }


    /**
     * Called when task item is long clicked
     *
     * @param taskId the task that needs to be edited
     */
    @Override
    public void onDeleteTask(long taskId) {
        viewModel.deleteTaskById(taskId);
    }

    /**
     * @return for test the adapter item count
     */
    @VisibleForTesting
    public int getTaskAdapterCount() {
        return adapter.getItemCount();
    }
}
