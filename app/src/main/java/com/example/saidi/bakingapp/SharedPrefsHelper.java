package com.example.saidi.bakingapp;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {

    public static final String MY_PREFS = "MY_PREFS";

    public static final String FAV_WIDGET = "FAV_WIDGET";

    SharedPreferences mSharedPreferences;

    public SharedPrefsHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    public void removeFav(int recipeId) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(FAV_WIDGET);
        editor.apply();
    }

    public void putFav(int recipeId) {
        mSharedPreferences.edit().putInt(FAV_WIDGET, recipeId).apply();
    }

    public int getFav() {
        return mSharedPreferences.getInt(FAV_WIDGET, -1);
    }

}
