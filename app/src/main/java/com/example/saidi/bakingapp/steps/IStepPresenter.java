package com.example.saidi.bakingapp.steps;


import com.example.saidi.bakingapp.BasePresenter;
import com.example.saidi.bakingapp.BaseView;
import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;

public interface IStepPresenter {

    interface View extends BaseView<IStepPresenter.Presenter> {
        void showStepDetail(Step step);

        void showError();

        void handleShowingNavigationArrow(int navigationArrowCode);

        void onNext();

        void onPrevious();
    }

    interface Presenter extends BasePresenter {
        void getStepDetail(Step step, Recipe recipe);

        void onNextClicked();

        void onPreviousClicked();

        void setArrowsVisibility();

        void setCurrentStep(Step step);
    }
}
