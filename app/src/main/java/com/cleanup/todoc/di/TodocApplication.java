package com.cleanup.todoc.di;

import android.app.Application;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class TodocApplication extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static Application getInstance() {
        return  instance;
    }
}
