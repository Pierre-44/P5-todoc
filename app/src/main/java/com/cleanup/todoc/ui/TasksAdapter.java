package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.entity.RelationTaskWithProject;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
//TestForListAdapter// public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener mDeleteTaskListener;
    /**
     * The list of tasks the adapter deals with
     */
    private List<RelationTaskWithProject> mRelationTaskWithProjects;
    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     * @param deleteTaskListener the delete task listener
     */
    public TasksAdapter(@NonNull DeleteTaskListener deleteTaskListener) {
        this.mDeleteTaskListener = deleteTaskListener;
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param relationTaskWithProjects the list of tasks the adapter deals with to set
     */
    void updateTasks(@NonNull List<RelationTaskWithProject> relationTaskWithProjects) {
        this.mRelationTaskWithProjects = relationTaskWithProjects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, mDeleteTaskListener);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {

        taskViewHolder.lblTaskName.setText(mRelationTaskWithProjects.get(position).getTask().getTaskName());
        taskViewHolder.lblProjectName.setText(mRelationTaskWithProjects.get(position).getProject().getProjectName());
        taskViewHolder.imgProject.setSupportImageTintList(ColorStateList.valueOf(mRelationTaskWithProjects.get(position).getProject().getProjectColor()));
        taskViewHolder.imgDelete.setTag(mRelationTaskWithProjects.get(position).getTask().getTaskId());

    }

    @Override
    public int getItemCount() {
        return mRelationTaskWithProjects != null? mRelationTaskWithProjects.size() : 0;
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param taskId the task with project that needs to be deleted
         */
        void onDeleteTask(int taskId);
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    static class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project
         */
        private final TextView lblProjectName;

        /**
         * The delete icon
         */
        private final AppCompatImageView imgDelete;

        /**
         * The listener for when a task needs to be deleted
         */
        private final DeleteTaskListener mDeleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView           the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        public TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.mDeleteTaskListener = deleteTaskListener;

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            imgDelete.setOnClickListener(view -> {
                final int tag = (int) view.getTag();
                TaskViewHolder.this.mDeleteTaskListener.onDeleteTask(tag);
            });
        }
    }
}