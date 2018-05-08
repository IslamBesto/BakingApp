package com.example.saidi.bakingapp;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.recipes.IRecipesPresenter;
import com.example.saidi.bakingapp.recipes.RecipesPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IRecipesPresenter.View {

    @BindView(R.id.recipes)
    TextView mRecipes;
    @BindView(R.id.container)
    ConstraintLayout mContainer;
    private IRecipesPresenter.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new RecipesPresenterImpl(this, this);
        mPresenter.start();
    }

    @Override
    public void setPresenter(IRecipesPresenter.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        mRecipes.setText(recipes.toString());
    }

    @Override
    public void showError(int errorCode) {
        Snackbar snackbar = Snackbar.make(mContainer, "Error", Snackbar.LENGTH_LONG);
    }
}
