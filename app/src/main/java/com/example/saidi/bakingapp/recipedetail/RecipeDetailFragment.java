package com.example.saidi.bakingapp.recipedetail;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;
import static com.example.saidi.bakingapp.Constants.KEY_STEP;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.saidi.bakingapp.R;
import com.example.saidi.bakingapp.RecipeWidgetService;
import com.example.saidi.bakingapp.SharedPrefManager;
import com.example.saidi.bakingapp.SharedPrefsHelper;
import com.example.saidi.bakingapp.StepActivity;
import com.example.saidi.bakingapp.Utils;
import com.example.saidi.bakingapp.data.model.Ingredient;
import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;
import com.example.saidi.bakingapp.recipedetail.customview.CardView;
import com.example.saidi.bakingapp.recipedetail.customview.IngredientView;
import com.example.saidi.bakingapp.recipedetail.customview.StepView;
import com.example.saidi.bakingapp.steps.StepDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeDetailFragment extends Fragment implements IRecipeDetailPresenter.View {

    @BindView(R.id.recipe_title)
    TextView mRecipeTitle;

    @BindView(R.id.serving_number)
    TextView mServingNumber;

    @BindView(R.id.ingredients)
    CardView mIngredients;

    @BindView(R.id.steps)
    CardView mSteps;

    @BindView(R.id.imageView)
    ImageView mRecipeImage;

    @BindView(R.id.detail_container)
    ScrollView mScrollView;

    @BindView(R.id.fav_switch)
    Switch mFavSwitch;

    private Recipe mRecipe;
    private IRecipeDetailPresenter.Presenter mPresenter;
    private StepView.StepClickListener mStepClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        mRecipe = getArguments().getParcelable(KEY_RECIPE);
        mPresenter = new RecipeDetailPresenterImpl(this, mRecipe);
        mPresenter.start();
        handleFavWidget();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(IRecipeDetailPresenter.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRecipeDetail(Recipe recipe) {
        mRecipeImage.setImageResource(Utils.getRecipeDrawable(recipe.getName()));
        mRecipeTitle.setText(recipe.getName());
        mServingNumber.setText(getString(R.string.servings, recipe.getServings().toString()));
        mStepClickListener = new StepView.StepClickListener() {
            @Override
            public void onStepClicked(Step step) {
                mPresenter.onStepClicked(step);
            }
        };
        setIngredient(recipe);
        setStep(recipe);
    }

    @Override
    public void showStep(Step step) {
        boolean isOnePane = getResources().getBoolean(R.bool.is_phone);
        if (isOnePane) {
            Intent stepIntent = new Intent(getActivity(), StepActivity.class);
            stepIntent.putExtra(KEY_STEP, step);
            stepIntent.putExtra(KEY_RECIPE, mRecipe);
            startActivity(stepIntent);
        } else {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Bundle recipeBundle = new Bundle();
            recipeBundle.putParcelable(KEY_RECIPE, mRecipe);
            recipeBundle.putParcelable(KEY_STEP, step);
            stepDetailFragment.setArguments(recipeBundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_fragment, stepDetailFragment)
                    .commit();
        }
    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar.make(mScrollView, getString(R.string.general_error),
                Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    private void setStep(Recipe recipe) {
        mSteps.setTitle(getString(R.string.step));
        LinearLayout linearLayout = (LinearLayout) mSteps.getContainer();
        for (Step step : recipe.getSteps()) {
            StepView stepView = new StepView(getContext());
            stepView.bind(step);
            stepView.setStepClickListener(mStepClickListener);
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

    private void handleFavWidget() {
        final SharedPrefManager sharedPrefManager = getSharedPrefManager();
        mFavSwitch.setChecked(sharedPrefManager.getFavId() == mRecipe.getId());
        mFavSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPrefManager.clear();
                    sharedPrefManager.saveFavId(mRecipe.getId());
                    RecipeWidgetService.startActionUpdateRecipeWidgets(getContext(), mRecipe);
                } else {
                    sharedPrefManager.removeFav(mRecipe.getId());
                }
            }
        });
    }

    private SharedPrefManager getSharedPrefManager() {
        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(
                getActivity().getApplicationContext());
        SharedPrefManager sharedPrefManager = new SharedPrefManager(sharedPrefsHelper);
        return sharedPrefManager;
    }
}
