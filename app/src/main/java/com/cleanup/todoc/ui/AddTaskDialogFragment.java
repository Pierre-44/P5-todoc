package com.cleanup.todoc.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.DialogAddTaskBinding;
import com.cleanup.todoc.di.TodocContainer;
import com.cleanup.todoc.di.ViewModelFactory;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.Task;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

/**
 * Created by pmeignen on 14/09/2021.
 */
public class AddTaskDialogFragment extends DialogFragment {

    private DialogAddTaskBinding binding;

    private AddTaskViewModel mAddTaskViewModel;
    private List<Project> allProjects;

    private AlertDialog dialog;
    @Nullable
    private EditText dialogEditText;
    @Nullable
    private Spinner dialogSpinner;

    public AddTaskDialogFragment() {
        // Empty constructor is required for DialogFragment
    }

    public static AddTaskDialogFragment newInstance() {

        return new AddTaskDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instancie viewmodel and container

        TodocContainer container = new TodocContainer(Objects.requireNonNull(getActivity()).getApplication());

        mAddTaskViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(
                container.getProjectRepository(),
                container.getTaskRepository(),
                Executors.newSingleThreadExecutor()))
                .get(AddTaskViewModel.class);

        //Get projects from our view model
        mAddTaskViewModel.getProjects().observe(this, projects -> {
            allProjects = projects;
            if (dialogSpinner != null){
                populateDialogSpinner(projects);
            }
        });

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> dialog.dismiss());

        dialog = alertBuilder.create();
        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner(allProjects);

        Button button = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(view -> onPositiveButtonClick(dialog));

        return dialog;
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner(List<Project> projects) {

        if (projects != null) {

            ArrayAdapter<Project> adapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, allProjects);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            if (dialogSpinner != null) {
                dialogSpinner.setAdapter(adapter);
            }
        }
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;

            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {

                Task task = new Task(
                        0,
                        taskProject.getProjectId(),
                        taskName,
                        new Date().getTime()
                );

                addTask(task);

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    // set binding at null when destroy view
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addTask(Task task) {
        mAddTaskViewModel.insertTask(task);
    }
}
