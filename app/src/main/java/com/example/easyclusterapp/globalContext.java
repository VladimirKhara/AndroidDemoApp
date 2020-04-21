package com.example.easyclusterapp;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.FragmentManager;

public class globalContext extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        globalContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return globalContext.context;
    }

}
