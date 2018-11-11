package com.example.saidi.bakingapp;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.saidi.bakingapp.data.model.Recipe;


public class RecipeWidgetService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RecipeWidgetService(String name) {
        super(name);
    }

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActionUpdateRecipeWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.putExtra(KEY_RECIPE, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            Recipe recipe = intent.getParcelableExtra(KEY_RECIPE);
            handleActionChangeRecipe(recipe);
        }
    }

    private void handleActionChangeRecipe(Recipe recipe) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeWidget(getBaseContext(), appWidgetManager, recipe,
                appWidgetIds);
    }
}
