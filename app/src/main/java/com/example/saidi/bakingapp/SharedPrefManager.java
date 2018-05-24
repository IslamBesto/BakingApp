package com.example.saidi.bakingapp;


public class SharedPrefManager {

    SharedPrefsHelper mSharedPrefsHelper;

    public SharedPrefManager(SharedPrefsHelper sharedPrefsHelper) {
        mSharedPrefsHelper = sharedPrefsHelper;
    }

    public void clear() {
        mSharedPrefsHelper.clear();
    }

    public void saveFavId(int id) {
        mSharedPrefsHelper.putFav(id);
    }

    public int getFavId() {
        return mSharedPrefsHelper.getFav();
    }

    public void removeFav(int recipeId) {
        mSharedPrefsHelper.removeFav(recipeId);
    }
}
