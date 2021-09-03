package com.cleanup.todoc.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.entity.Project;
import com.cleanup.todoc.model.entity.Task;

import java.util.concurrent.Executors;

/**
 * Created by pmeignen on 30/08/2021.
 */
@Database(entities = {Project.class , Task.class}, exportSchema = false, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    // instance
    private static TodocDatabase instanceDB;

    // Dao Interfaces
    public abstract ProjectDao mProjectDao();
    public abstract TaskDao mTaskDao();

    // Singleton
    public static synchronized TodocDatabase getInstance(Context context) {
        if(instanceDB == null) {
            instanceDB = Room.databaseBuilder(context.getApplicationContext(), TodocDatabase.class, "todoc_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instanceDB;
    }

    public static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateProjectOnDb();
        }
    };

    public static void populateProjectOnDb(){
        Executors.newFixedThreadPool(3).execute(()-> {
            instanceDB.mProjectDao().insert(new Project(1L,"Projet Tartampion",0xFFEADAD1));
            instanceDB.mProjectDao().insert(new Project(2L,"Projet Lucidia",0xFFB4CDBA));
            instanceDB.mProjectDao().insert(new Project(3L,"Projet Circus",0xFFA3CED2));
        });
    }
}
