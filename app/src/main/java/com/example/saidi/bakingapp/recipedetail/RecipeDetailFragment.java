package com.example.saidi.bakingapp.recipedetail;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saidi.bakingapp.R;
import com.example.saidi.bakingapp.data.model.Recipe;


public class RecipeDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        Recipe recipe = getArguments().getParcelable(KEY_RECIPE);
        return rootView;
    }
}
