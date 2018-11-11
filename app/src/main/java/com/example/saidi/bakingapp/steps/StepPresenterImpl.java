package com.example.saidi.bakingapp.steps;


import com.example.saidi.bakingapp.Constants;
import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;

public class StepPresenterImpl implements IStepPresenter.Presenter {

    private final IStepPresenter.View mStepView;
    private Recipe mRecipe;
    private Step mStep;

    public StepPresenterImpl(IStepPresenter.View view, Step step, Recipe recipe) {
        mStep = step;
        mRecipe = recipe;
        mStepView = view;
    }

    @Override
    public void start() {
        if (mRecipe != null) {
            getStepDetail(mStep, mRecipe);
        }
    }


    @Override
    public void getStepDetail(Step step, Recipe recipe) {
        if (step != null) {
            mStepView.showStepDetail(step);
        } else {
            mStepView.showError();
        }
    }

    @Override
    public void onNextClicked() {
        mStepView.onNext();
    }

    @Override
    public void onPreviousClicked() {
        mStepView.onPrevious();
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

    @Override
    public void setCurrentStep(Step step) {
        mStep = step;
    }
}
