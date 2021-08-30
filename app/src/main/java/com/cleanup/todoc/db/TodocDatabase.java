package com.cleanup.todoc.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.Task;
import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;

/**
 * Created by pmeignen on 30/08/2021.
 */
@Database(entities = {Project.class , Task.class}, exportSchema = false, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    public final String DB_NAME = "todoc_database";
    private static TodocDatabase instance;

    public abstract ProjectDao mProjectDao();

    public abstract TaskDao mTaskDao();


}
