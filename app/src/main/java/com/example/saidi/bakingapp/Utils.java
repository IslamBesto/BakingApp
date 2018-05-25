package com.example.saidi.bakingapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static boolean isConnectivityAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    public static int getRecipeDrawable(String recipeName) {
        int drawable = -1;
        switch (recipeName) {
            case "Nutella Pie":
                drawable = R.drawable.nutella_pie;
                break;
            case "Brownies":
                drawable = R.drawable.brownies;
                break;
            case "Yellow Cake":
                drawable = R.drawable.yellow;
                break;
            case "Cheesecake":
                drawable = R.drawable.cheescake;
                break;
            default:
                drawable = R.drawable.recipe_placeholder;
                break;
        }
        return drawable;
    }

}
