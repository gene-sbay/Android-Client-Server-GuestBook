package com.demo.guestbook.prefs;

import android.app.Activity;
import android.content.SharedPreferences;

import com.demo.guestbook.util.TheApp;
import com.google.gson.Gson;


/**
 * Default visibility so that all SharedPref actions are accessed via subclass in same package,
 * with dedicated accessor methods for each preference key/value pair.
 */
abstract class SharedPrefsJsonDao {

    abstract protected String getSharedPreferencesObjectName();

    private SharedPreferences.Editor getPreferencesEditor() {

        SharedPreferences settings = getSharedPreferences();

        SharedPreferences.Editor editor = settings.edit();

        return editor;
    }

    private SharedPreferences getSharedPreferences() {

        SharedPreferences settings = TheApp.getAppContext().getSharedPreferences(getSharedPreferencesObjectName(), 0);

        return settings;
    }

    protected String getPrefString(String prefName) {

        SharedPreferences settings = getSharedPreferences();

        String value = settings.getString(prefName, "");

        return value;
    }

    /**
     * Default is false
     * 
     * @param prefName
     * @return
     */
    protected Boolean getPrefBool(String prefName) {

        SharedPreferences settings = getSharedPreferences();

        Boolean value = settings.getBoolean(prefName, false);

        return value;
    }

    protected void updatePrefs(String prefName, String value) {

        SharedPreferences.Editor editor = getPreferencesEditor();

        editor.putString(prefName, value);
        editor.commit();
    }

    protected void updatePrefs(String prefName, boolean value) {

        SharedPreferences.Editor editor = getPreferencesEditor();

        editor.putBoolean(prefName, value);
        editor.commit();
    }

    protected void writeSerializableToJson(Object serializableObj, String prefKey) {
        Gson gson = new Gson();
        String sharedPrefJsonStr = gson.toJson(serializableObj);
        updatePrefs(prefKey, sharedPrefJsonStr);
    }

    protected String getSharedPrefJsonString() {
        String encodedJsonStr = getPrefString(getSharedPreferencesObjectName());
        if ( encodedJsonStr.isEmpty()) {
            return "";
        }
        return encodedJsonStr;
    }

}


