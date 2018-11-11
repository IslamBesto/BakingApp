package com.example.saidi.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.recipes.IRecipesPresenter;
import com.example.saidi.bakingapp.recipes.RecipesListAdapter;
import com.example.saidi.bakingapp.recipes.RecipesPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;

public class MainActivity extends AppCompatActivity implements IRecipesPresenter.View {

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecipesRecycler;

    @BindView(R.id.container)
    FrameLayout mContainer;

    @BindView(R.id.recipes_progress)
    ProgressBar mRecipesProgress;

    RecipesListAdapter.RecipeListClickListener mRecipeListClickListener;

    private IRecipesPresenter.Presenter mPresenter;

    @Nullable
    private MainIdlingResource mMainIdlingResource;

    private int mIndex = -1;
    private int mTop = -1;
    private boolean mIsPhone;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mMainIdlingResource == null) {
            mMainIdlingResource = new MainIdlingResource();
        }
        return mMainIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecipesRecycler.setLayoutManager(getLayoutManager());

        mRecipeListClickListener = new RecipesListAdapter.RecipeListClickListener() {
            @Override
            public void onRecipeClicked(View view, int position, Recipe recipe) {

                mPresenter.onRecipeClicked(recipe);
            }
        };

        mPresenter = new RecipesPresenterImpl(this, this, (MainIdlingResource) getIdlingResource());
        mPresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsPhone) {
            mIndex = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else {
            mIndex = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        }
        View v = mRecipesRecycler.getChildAt(0);
        mTop = (v == null) ? 0 : (v.getTop() - mRecipesRecycler.getPaddingTop());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIndex != -1 && mIsPhone) {
            ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(mIndex, mTop);
        } else if (!mIsPhone) {
            ((GridLayoutManager) getLayoutManager()).scrollToPositionWithOffset(mIndex, mTop);
        }
    }

    @Override
    public void setPresenter(IRecipesPresenter.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        RecipesListAdapter recipesListAdapter = new RecipesListAdapter(recipes,
                mRecipeListClickListener);
        mRecipesRecycler.setAdapter(recipesListAdapter);
    }

    @Override
    public void showRecipeDetail(Recipe recipe) {
        Intent recipeDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
        recipeDetailIntent.putExtra(KEY_RECIPE, recipe);
        startActivity(recipeDetailIntent);
    }

    @Override
    public void showError(int errorCode) {
        String messageError = getString(R.string.general_error);
        if (errorCode == 1) {
            messageError = getString(R.string.connectivity_error);
        }
        Snackbar snackbar = Snackbar.make(mContainer, messageError, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showLoader() {
        mRecipesProgress.setVisibility(View.VISIBLE);
        mRecipesRecycler.setVisibility(View.GONE);
    }

    @Override
    public void hideLoader() {
        mRecipesProgress.setVisibility(View.GONE);
        mRecipesRecycler.setVisibility(View.VISIBLE);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        mIsPhone = getResources().getBoolean(R.bool.is_phone);
        RecyclerView.LayoutManager layoutManager;
        if (mIsPhone) {
            layoutManager = new LinearLayoutManager(this);
        } else {
            layoutManager = new GridLayoutManager(this, 3);
        }
        return layoutManager;
    }
}
