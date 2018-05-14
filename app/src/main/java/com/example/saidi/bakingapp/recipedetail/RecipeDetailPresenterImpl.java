package com.example.saidi.bakingapp.recipedetail;

import com.example.saidi.bakingapp.data.model.Recipe;


public class RecipeDetailPresenterImpl implements IRecipeDetailPresenter.Presenter {

    private Recipe mRecipe;
    private IRecipeDetailPresenter.View mRecipeDetailView;


    public RecipeDetailPresenterImpl(IRecipeDetailPresenter.View view, Recipe recipe) {
        mRecipe = recipe;
        mRecipeDetailView = view;
    }

    @Override
    public void start() {
        getRecipe(mRecipe);
    }

    @Override
    public void getRecipe(Recipe recipe) {
        if (recipe != null) {
            mRecipeDetailView.showRecipeDetail(recipe);
        } else {
            mRecipeDetailView.showError(1);
        }
    }
}
