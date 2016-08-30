package com.demo.guestbook.util;

import android.app.Application;
import android.content.Context;

import com.demo.guestbook.R;

public class TheApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        TheApp.context = getApplicationContext();
    }

    public static String findString(int stringId) {
        return TheApp.getAppContext().getString(stringId);
    }

    public static Context getAppContext() {
        return TheApp.context;
    }
}