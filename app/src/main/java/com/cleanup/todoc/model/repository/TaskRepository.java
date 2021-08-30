package com.cleanup.todoc.model.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.dao.TaskDao;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class TaskRepository {

    private final TaskDao mTaskDao;
    private final LiveData<List> tbdLivedata; // TDOO : define livedata
    private final Executor doInBackground;

    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
        doInBackground = null; // TODO : complete this
    }

}
