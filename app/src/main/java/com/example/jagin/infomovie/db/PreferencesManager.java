package com.example.jagin.infomovie.db;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String SHARED_PREFS_FILE="preferencess";
    private static final String MUSIC_STATE="music";

    private Context context;

    public PreferencesManager(Context context) {
        this.context = context;
    }

    private SharedPreferences getPreferences()
    {
        return context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public boolean isMusicEnabled(){ return getPreferences().getBoolean(MUSIC_STATE, true);}

    public void setMusicEnabled(boolean state)
    {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(MUSIC_STATE, state);
        editor.apply();
    }
}
