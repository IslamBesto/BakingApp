package com.example.saidi.bakingapp.recipedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saidi.bakingapp.R;
import com.example.saidi.bakingapp.data.model.Ingredient;
import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;
import com.example.saidi.bakingapp.recipedetail.customview.CardView;
import com.example.saidi.bakingapp.recipedetail.customview.IngredientView;
import com.example.saidi.bakingapp.recipedetail.customview.StepView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;


public class RecipeDetailFragment extends Fragment implements IRecipeDetailPresenter.View {

    @BindView(R.id.recipe_title)
    TextView mRecipeTitle;

    @BindView(R.id.serving_number)
    TextView mServingNumber;

    @BindView(R.id.ingredients)
    CardView mIngredients;

    @BindView(R.id.steps)
    CardView mSteps;

    private Recipe mRecipe;
    private IRecipeDetailPresenter.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        mRecipe = getArguments().getParcelable(KEY_RECIPE);
        mPresenter = new RecipeDetailPresenterImpl(this, mRecipe);
        mPresenter.start();
        return rootView;
    }

    @Override
    public void setPresenter(IRecipeDetailPresenter.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRecipeDetail(Recipe recipe) {
        mRecipeTitle.setText(recipe.getName());
        mServingNumber.setText(getString(R.string.servings, recipe.getServings().toString()));
        setIngredient(recipe);
        mSteps.setTitle("Steps");
        LinearLayout linearLayout = (LinearLayout) mSteps.getContainer();
        for (Step step : recipe.getSteps()) {
            StepView stepView = new StepView(getContext());
            stepView.bind(step);
            linearLayout.addView(stepView);
        }


    }

    private void setIngredient(Recipe recipe) {
        mIngredients.setTitle(getString(R.string.ingredient));
        LinearLayout linearLayout = (LinearLayout) mIngredients.getContainer();
        for (Ingredient ingredient : recipe.getIngredients()) {
            IngredientView ingredientView = new IngredientView(getContext());
            ingredientView.bind(ingredient);
            linearLayout.addView(ingredientView);
        }
    }

    @Override
    public void showError(int errorCode) {

    }
}
