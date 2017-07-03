package com.crossover.auctilion.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by heghine on 2/11/17.
 */

public class PreferenceManager {

    private static PreferenceManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String NAME = "Auctilion";

    private PreferenceManager() {

    }

    public static PreferenceManager getInstance() {
        if (instance == null) {
            instance = new PreferenceManager();
        }
        return instance;
    }

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
}
