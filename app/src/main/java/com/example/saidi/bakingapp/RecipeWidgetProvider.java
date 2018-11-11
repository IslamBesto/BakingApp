package com.example.saidi.bakingapp;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.saidi.bakingapp.data.model.Ingredient;
import com.example.saidi.bakingapp.data.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static Recipe mRecipe;

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
            Recipe recipe, int appWidgetId) {

        mRecipe = recipe;
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.recipe_widget_provider);
        // views.setTextViewText(R.id.appwidget_text, widgetText);
        PendingIntent pendingIntent;
        Intent intent = new Intent(context, MainActivity.class);
        if (recipe != null) {
            Intent intentDetail = new Intent(context, DetailActivity.class);
            intentDetail.putExtra(KEY_RECIPE, recipe);
            intentDetail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pendingIntent = PendingIntent.getActivity(context, 0, intentDetail,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }

        views.setOnClickPendingIntent(R.id.note_image, pendingIntent);
        if (recipe != null) {
            views.setTextViewText(R.id.recipe_title, recipe.getName());
            StringBuilder ingredientString = new StringBuilder();
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredientString.append(ingredient.getIngredient());
                ingredientString.append(System.getProperty("line.separator"));
            }
            views.setTextViewText(R.id.ingredient, ingredientString);
        } else {
            views.setTextViewText(R.id.recipe_title, context.getString(R.string.choose_fav));
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
            Recipe recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeWidgetService.startActionUpdateRecipeWidgets(context, mRecipe);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

