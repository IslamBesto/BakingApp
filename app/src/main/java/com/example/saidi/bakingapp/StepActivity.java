package com.example.saidi.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;
import com.example.saidi.bakingapp.steps.StepDetailFragment;

import butterknife.ButterKnife;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;
import static com.example.saidi.bakingapp.Constants.KEY_STEP;

public class StepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        Intent recipeDetailIntent = getIntent();
        Recipe recipe = recipeDetailIntent.getParcelableExtra(KEY_RECIPE);
        Step step = recipeDetailIntent.getParcelableExtra(KEY_STEP);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable(KEY_RECIPE, recipe);
        recipeBundle.putParcelable(KEY_STEP, step);
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setArguments(recipeBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_detail_fragment, stepDetailFragment)
                .commit();
    }
}
