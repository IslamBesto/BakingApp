package com.example.saidi.bakingapp.steps;


import com.example.saidi.bakingapp.Constants;
import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;

public class StepPresenterImpl implements IStepPresenter.Presenter {

    private final Recipe mRecipe;
    private final Step mStep;
    private final IStepPresenter.View mStepView;

    public StepPresenterImpl(IStepPresenter.View view, Step step, Recipe recipe) {
        mStep = step;
        mRecipe = recipe;
        mStepView = view;
    }

    @Override
    public void start() {
        getStepDetail(mStep, mRecipe);
    }


    @Override
    public void getStepDetail(Step step, Recipe recipe) {
        if (step != null) {
            mStepView.showStepDetail(step);
        } else {
            mStepView.showError(1);
        }
    }

    @Override
    public void setArrowsVisibility() {
        if (mStep.getId() == 0) {
            mStepView.handleShowingNavigationArrow(Constants.KEY_SHOW_ONLY_NEXT);
        } else if (mStep.getId() == mRecipe.getSteps().size() - 1) {
            mStepView.handleShowingNavigationArrow(Constants.KEY_SHOW_ONLY_PREVIOUS);
        } else {
            mStepView.handleShowingNavigationArrow(Constants.KEY_SHOW_BOTH);
        }
    }
}
