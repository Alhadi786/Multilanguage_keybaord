package com.multilanguage.notes.notepad;

import android.app.Application;
import android.util.Log;

/**
 * Created by Azeem on 8/13/2016.
 */
public class MainApplication extends Application {

    private static AppPreferences sAppPreferences;
    private static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {

        super.onCreate();
        sAppPreferences = new AppPreferences(this);
        Log.d(TAG, "onCreate: ");
    }

    public static AppPreferences getAppPreferences() {
        return sAppPreferences;
    }
}
