package com.healthtracker.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    // private static final String tag = "babyrecipes-storedata";
    private SharedPreferences pref = null;
    private Context parentActivity;
    public static final String HEALTH_TRACKER_INFO = "health_tracker_info";

    public PreferenceHelper(Context context) {
        parentActivity = context;
    }

    public void setString(String key, String value) {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        return pref.getString(key, "");

    }

    public void setBoolean(String key, boolean value) {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public boolean is_exist(String key) {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        if (pref.contains(key)) {
            return true;
        }

        return false;
    }

    public int getInteger(String key) {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public void setInteger(String key, int value) {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void clearAll() {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public void removeKey(String key) {
        pref = parentActivity.getSharedPreferences(HEALTH_TRACKER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();

    }
}
