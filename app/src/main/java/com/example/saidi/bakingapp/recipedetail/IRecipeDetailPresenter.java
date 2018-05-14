package com.example.saidi.bakingapp.recipedetail;

import com.example.saidi.bakingapp.BasePresenter;
import com.example.saidi.bakingapp.BaseView;
import com.example.saidi.bakingapp.data.model.Recipe;

/**
 * Created by saidi on 13/05/2018.
 */

public interface IRecipeDetailPresenter {
    interface View extends BaseView<IRecipeDetailPresenter.Presenter> {
        void showRecipeDetail(Recipe recipe);

        void showError(int errorCode);
    }

    interface Presenter extends BasePresenter {
        void getRecipe(Recipe recipe);
    }
}
