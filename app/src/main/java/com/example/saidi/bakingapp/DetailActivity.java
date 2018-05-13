package com.example.saidi.bakingapp;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.saidi.bakingapp.recipedetail.RecipeDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        Intent recipeDetailIntent = getIntent();
        Parcelable recipe = recipeDetailIntent.getExtras().getParcelable(KEY_RECIPE);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable(KEY_RECIPE, recipe);
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        recipeDetailFragment.setArguments(recipeBundle);
        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_fragment, recipeDetailFragment)
                .commit();
    }
}
