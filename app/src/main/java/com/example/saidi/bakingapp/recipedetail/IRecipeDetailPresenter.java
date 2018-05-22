package com.example.saidi.bakingapp.recipedetail;

import com.example.saidi.bakingapp.BasePresenter;
import com.example.saidi.bakingapp.BaseView;
import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;


public interface IRecipeDetailPresenter {
    interface View extends BaseView<IRecipeDetailPresenter.Presenter> {
        void showRecipeDetail(Recipe recipe);

        void showStep(Step step);

        void showError();
    }

    interface Presenter extends BasePresenter {
        void getRecipe(Recipe recipe);

        void onStepClicked(Step step);
    }
}
