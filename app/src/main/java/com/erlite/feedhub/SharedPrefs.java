package com.erlite.feedhub;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class SharedPrefs {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public int themeSettings;

    public SharedPrefs(Context context){

        sharedPreferences = context.getSharedPreferences("trin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        themeSettings = sharedPreferences.getInt("theme", 1);

    }


    public void saveTheme(Context context){

        themeSettings = AppCompatDelegate.getDefaultNightMode();

        sharedPreferences = context.getSharedPreferences("trin", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putInt("theme", themeSettings);
        editor.apply();

    }


}
