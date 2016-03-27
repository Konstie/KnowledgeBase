package com.app.knowledgebase.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.knowledgebase.KnowledgeBaseApplication;

public class PreferencesHelper {
    private static final String PREFS_STRATEGIES_SAVED = "PREFS_STRATEGIES_SAVED";

    private static PreferencesHelper prefHelper;
    private SharedPreferences sharedPrefs;

    private PreferencesHelper() {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(KnowledgeBaseApplication.getContext());
    }

    public static synchronized PreferencesHelper get() {
        if (prefHelper == null) {
            prefHelper = new PreferencesHelper();
        }
        return prefHelper;
    }

    private static SharedPreferences getSharedPreferences(String name) {
        return KnowledgeBaseApplication.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void setStrategiesSaved(boolean strategiesSaved) {
        sharedPrefs.edit().putBoolean(PREFS_STRATEGIES_SAVED, strategiesSaved).apply();
    }

    public boolean areStrategiesSaved() {
        return sharedPrefs.getBoolean(PREFS_STRATEGIES_SAVED, false);
    }
}
