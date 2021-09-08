package com.cleanup.todoc.di;

import android.app.Application;

/**
 * Created by pmeignen on 30/08/2021.
 */
public class TodocApplication extends Application {

    public static TodocContainer sTodocContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        sTodocContainer = new TodocContainer(this);
    }
}
