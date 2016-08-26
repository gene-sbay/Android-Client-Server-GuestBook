package com.demo.guestbook.util;

import android.app.Application;
import android.content.Context;

public class TheApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        TheApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return TheApp.context;
    }
}