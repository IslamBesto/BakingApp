package com.example.saidi.bakingapp;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.recipes.IRecipesPresenter;
import com.example.saidi.bakingapp.recipes.RecipesListAdapter;
import com.example.saidi.bakingapp.recipes.RecipesPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IRecipesPresenter.View {

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecipesRecycler;

    @BindView(R.id.container)
    ConstraintLayout mContainer;

    RecipesListAdapter.RecipeListClickListener mRecipeListClickListener;

    private IRecipesPresenter.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecipesRecycler.setLayoutManager(layoutManager);

        mRecipeListClickListener = new RecipesListAdapter.RecipeListClickListener() {
            @Override
            public void onRecipeClicked(View view, int position, Recipe recipe) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        };

        mPresenter = new RecipesPresenterImpl(this, this);
        mPresenter.start();
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
    public void showError(int errorCode) {
        String messageError = getString(R.string.general_error);
        if(errorCode == 1){
            messageError = getString(R.string.connectivity_error);
        }
        Snackbar snackbar = Snackbar.make(mContainer, messageError, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
