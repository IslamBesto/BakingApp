package com.example.saidi.bakingapp.recipes;


import android.content.Context;

import com.example.saidi.bakingapp.MainIdlingResource;
import com.example.saidi.bakingapp.Utils;
import com.example.saidi.bakingapp.data.RecipeAPI;
import com.example.saidi.bakingapp.data.ServiceManager;
import com.example.saidi.bakingapp.data.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesPresenterImpl implements IRecipesPresenter.Presenter {

    private Context mContext;
    private IRecipesPresenter.View mRecipesView;
    private MainIdlingResource mMainIdlingResource;


    public RecipesPresenterImpl(Context context,
            IRecipesPresenter.View view, MainIdlingResource mainIdlingResource) {
        mContext = context;
        mRecipesView = view;
        mMainIdlingResource = mainIdlingResource;
    }

    @Override
    public void start() {
        getAllRecipes();
    }

    @Override
    public void getAllRecipes() {
        mMainIdlingResource.setIdleState(false);
        mRecipesView.showLoader();
        ServiceManager.createService(RecipeAPI.class).getAllRecipes().enqueue(
                new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call,
                                           Response<List<Recipe>> response) {
                        mRecipesView.showRecipes(response.body());
                        mMainIdlingResource.setIdleState(true);
                        mRecipesView.hideLoader();
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        if (!Utils.isConnectivityAvailable(mContext)) {
                            mRecipesView.showError(1);
                        }

                    }
                });
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        mRecipesView.showRecipeDetail(recipe);
    }
}
