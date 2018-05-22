package com.example.saidi.bakingapp.steps;


import android.content.res.Configuration;
import android.net.Uri;
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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.saidi.bakingapp.Constants.KEY_RECIPE;
import static com.example.saidi.bakingapp.Constants.KEY_SHOW_BOTH;
import static com.example.saidi.bakingapp.Constants.KEY_SHOW_ONLY_NEXT;
import static com.example.saidi.bakingapp.Constants.KEY_SHOW_ONLY_PREVIOUS;
import static com.example.saidi.bakingapp.Constants.KEY_STEP;

public class StepDetailFragment extends Fragment implements IStepPresenter.View {

    @BindView(R.id.step_description)
    TextView mStepDescription;

    @BindView(R.id.next)
    ImageView mNextArrow;

    @BindView(R.id.previous)
    ImageView mPreviousArrow;

    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    private SimpleExoPlayer mExoPlayer;


    private Recipe mRecipe;
    private IStepPresenter.Presenter mPresenter;
    private Integer mStepId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        setRetainInstance(true);
        ButterKnife.bind(this, rootView);
        mRecipe = getArguments().getParcelable(KEY_RECIPE);
        Step mStep = getArguments().getParcelable(KEY_STEP);
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
        mStepId = step.getId();
        initializePlayer(step.getVideoURL());

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

    @Override
    public void onNext() {
        Step stepToShow = mRecipe.getSteps().get(mStepId + 1);
        mPresenter.setCurrentStep(stepToShow);
        releasePlayer();
        showStepDetail(stepToShow);
    }

    @Override
    public void onPrevious() {
        Step stepToShow = mRecipe.getSteps().get(mStepId - 1);
        mPresenter.setCurrentStep(stepToShow);
        releasePlayer();
        showStepDetail(stepToShow);
    }

    @OnClick(R.id.next)
    public void onNextClicked() {
        mPresenter.onNextClicked();
    }

    @OnClick(R.id.previous)
    public void onPreviousClicked() {
        mPresenter.onPreviousClicked();
    }

    private void initializePlayer(String videoUrl) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "bakingapp");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getActivity().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            mStepDescription.setVisibility(View.GONE);
            mPreviousArrow.setVisibility(View.GONE);
            mNextArrow.setVisibility(View.GONE);

        } else {
            mStepDescription.setVisibility(View.VISIBLE);
            mPreviousArrow.setVisibility(View.VISIBLE);
            mNextArrow.setVisibility(View.VISIBLE);
        }
    }
}
