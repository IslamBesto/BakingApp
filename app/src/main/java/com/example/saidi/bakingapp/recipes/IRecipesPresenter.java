package com.example.saidi.bakingapp.recipes;

import com.example.saidi.bakingapp.BasePresenter;
import com.example.saidi.bakingapp.BaseView;
import com.example.saidi.bakingapp.data.model.Recipe;

import java.util.List;

public interface IRecipesPresenter {

    interface View extends BaseView<Presenter> {
        void showRecipes(List<Recipe> recipes);
        void showRecipeDetail(Recipe recipe);
        void showError(int errorCode);
    }

    interface Presenter extends BasePresenter {
        void getAllRecipes();

        void onRecipeClicked(Recipe recipe);
    }
}
