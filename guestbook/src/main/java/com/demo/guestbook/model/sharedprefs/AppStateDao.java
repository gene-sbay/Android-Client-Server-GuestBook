package com.demo.guestbook.model.sharedprefs;

import com.demo.guestbook.util.Const;
import com.google.gson.Gson;

/**
 * This class functions as a GateKeeper to AppState information from SharedPrefs
 * There are only 2 accessible methods:
 *  - getAppState()
 *  - sharedPrefs_updateAppState(AppState appState)
 *
 *  All other code is encapsulated
 */
public class AppStateDao extends SharedPrefsJsonDao {

    private static AppStateDao sAppStateDao;
    private static AppState sAppState;

    public static AppState getAppState() {

        if (sAppState == null) {
            getAppStateDao().sharedPrefs_loadAppState();
        }

        return sAppState;
    }

    public static void sharedPrefs_updateAppState(AppState appState) {

        getAppStateDao().writeSerializableToJson(appState, Const.SharedPrefs.PREF__APP_STATE);

        getAppStateDao().setSingleton(appState);
    }

    private AppStateDao() {}

    @Override
    protected String getSharedPreferencesObjectName() {
        return Const.SharedPrefs.PREF__APP_STATE;
    }

    protected void resetSingleton() {

        AppState appState = new AppState();

        sharedPrefs_updateAppState(appState);

        setSingleton(appState);
    }

    /**
     * Making accessible to reset
     *
     * @param appState
     */
    protected void setSingleton(AppState appState) {

        sAppState = appState;
    }

    private static AppStateDao getAppStateDao() {

        if (sAppStateDao == null) {
            sAppStateDao = new AppStateDao();
        }

        return sAppStateDao;
    }

    /**
     * Tries to load existing AppState
     * If there isn't one, sets one into SharedPref
     */
    protected void sharedPrefs_loadAppState() {

        String appStateJsonStr = getSharedPrefJsonString();

        // If no data, create and save to prefs
        if (appStateJsonStr.isEmpty()) {
            resetSingleton();
            return;
        }

        // Load existing data
        Gson gson = new Gson();
        AppState appState = gson.fromJson(appStateJsonStr, AppState.class);
        setSingleton(appState);
    }

}