package com.example.saidi.bakingapp.steps;


import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;
import static com.example.saidi.bakingapp.Constants.KEY_SHOW_BOTH;
import static com.example.saidi.bakingapp.Constants.KEY_SHOW_ONLY_NEXT;
import static com.example.saidi.bakingapp.Constants.KEY_SHOW_ONLY_PREVIOUS;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saidi.bakingapp.R;
import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment implements IStepPresenter.View {

    @BindView(R.id.step_description)
    TextView mStepDescription;

    @BindView(R.id.next)
    ImageView mNextArrow;

    @BindView(R.id.previous)
    ImageView mPreviousArrow;

    private Recipe mRecipe;
    private Step mStep;
    private IStepPresenter.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);
        mRecipe = getArguments().getParcelable(KEY_RECIPE);
        mStep = getArguments().getParcelable("step");
        mPresenter = new StepPresenterImpl(this, mStep, mRecipe);
        mPresenter.start();
        return rootView;
    }

    @Override
    public void setPresenter(IStepPresenter.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showStepDetail(Step step) {
        mStepDescription.setText(step.getDescription());
        mPresenter.setArrowsVisibility();
    }

    @Override
    public void showError(int errorCode) {

    }

    @Override
    public void handleShowingNavigationArrow(int navigationArrowCode) {
        switch (navigationArrowCode) {
            case KEY_SHOW_BOTH:
                mNextArrow.setVisibility(View.VISIBLE);
                mPreviousArrow.setVisibility(View.VISIBLE);
                break;
            case KEY_SHOW_ONLY_NEXT:
                mNextArrow.setVisibility(View.VISIBLE);
                mPreviousArrow.setVisibility(View.GONE);
                break;
            case KEY_SHOW_ONLY_PREVIOUS:
                mNextArrow.setVisibility(View.GONE);
                mPreviousArrow.setVisibility(View.VISIBLE);
                break;
        }
    }
}
