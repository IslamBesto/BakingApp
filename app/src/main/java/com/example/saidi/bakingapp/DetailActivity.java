package com.example.saidi.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.recipedetail.RecipeDetailFragment;

import butterknife.ButterKnife;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        boolean isTowPane = getResources().getBoolean(R.bool.is_phone);
        Intent recipeDetailIntent = getIntent();
        Recipe recipe = recipeDetailIntent.getParcelableExtra(KEY_RECIPE);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable(KEY_RECIPE, recipe);
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        recipeDetailFragment.setArguments(recipeBundle);
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_fragment, recipeDetailFragment)
                .commit();

     /*   if (!isTowPane) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(recipeBundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        }*/

    }
}
