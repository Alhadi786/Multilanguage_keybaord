package com.multilanguage.notes.notepad;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Azeem on 8/13/2016.
 */
public class AppPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    //Settings Preferences
    public static final String KeyFirstRun = "prefFirstRun";
    public static final String KeyDefaultLanguage = "prefDefaultLanguage";
    public static final String KeyIndex = "prefIndex";


    public AppPreferences(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = sharedPreferences.edit();
        this.context = context;
    }
    public boolean getIsFirstRun(){
        return sharedPreferences.getBoolean(KeyFirstRun, false);
    }
    public  void setIsFirstRun(boolean isFirstRun){
        editor.putBoolean(KeyFirstRun, isFirstRun);
        editor.commit();
    }

    public  void setLanguage(String language){
      editor.putString(KeyDefaultLanguage,language);
        editor.commit();
    }
    public  String getLanguage(){
        return sharedPreferences.getString(KeyDefaultLanguage, "");


    }

    public void setIndex(int index){
        editor.putInt(KeyIndex,index);
        editor.commit();

    }
    public  int getIndex(){
        return sharedPreferences.getInt(KeyIndex,0);
    }
}
