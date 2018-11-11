package com.example.saidi.bakingapp.steps;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.saidi.bakingapp.R;
import com.example.saidi.bakingapp.data.model.Recipe;
import com.example.saidi.bakingapp.data.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;
import static com.example.saidi.bakingapp.Constants.KEY_PLAYER_POSITION;
import static com.example.saidi.bakingapp.Constants.KEY_PLAY_WHEN_READY;
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
    PlayerView mPlayerView;

    @BindView(R.id.step_container)
    ScrollView mScrollView;

    @BindView(R.id.exo_pip)
    FrameLayout mPictureInPicture;

    @BindView(R.id.video_thumbnail)
    ImageView mVideoThumbnail;

    private SimpleExoPlayer mExoPlayer;

    private PlayerControlView mControlView;


    private Recipe mRecipe;
    private IStepPresenter.Presenter mPresenter;
    private Integer mStepId;
    private boolean mPlayWhenReady = true;
    private long mSeekPosition = 0;
    private boolean isOnePane;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        setRetainInstance(true);
        ButterKnife.bind(this, rootView);
        mRecipe = getArguments().getParcelable(KEY_RECIPE);
        Step mStep = getArguments().getParcelable(KEY_STEP);
        mPresenter = new StepPresenterImpl(this, mStep, mRecipe);
        toPictureInPicture();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //saving the player position if available
        if (mExoPlayer != null) {
            outState.putLong(KEY_PLAYER_POSITION, mExoPlayer.getCurrentPosition());
            outState.putBoolean(KEY_PLAY_WHEN_READY, mExoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mSeekPosition = savedInstanceState.getLong(KEY_PLAYER_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        if (isInPictureInPictureMode) {
            mPlayerView.hideController();
        } else {
            mPlayerView.showController();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void setPresenter(IStepPresenter.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showStepDetail(Step step) {
        isOnePane = getResources().getBoolean(R.bool.is_phone);
        mStepDescription.setText(step.getDescription());
        String thumbnailURL = step.getThumbnailURL();
        if (thumbnailURL != null && !thumbnailURL.isEmpty()) {
            Picasso.with(getContext()).load(thumbnailURL).into(mVideoThumbnail);
        } else {
            mVideoThumbnail.setVisibility(View.GONE);
        }
        mPresenter.setArrowsVisibility();
        mStepId = step.getId();
        if (step.getVideoURL().equals("") || step.getVideoURL().equals(null)) {
            mPlayerView.setVisibility(View.GONE);
        } else {
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(step.getVideoURL());
        }
        if (!isOnePane) {
            mNextArrow.setVisibility(View.GONE);
            mPreviousArrow.setVisibility(View.GONE);
        }

    }

    @Override
    public void showError() {
        Snackbar snackbar = Snackbar.make(mScrollView, getString(R.string.general_error),
                Snackbar.LENGTH_LONG);
        snackbar.show();
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
            BandwidthMeter bwMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bwMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "bakingapp");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                    new DefaultDataSourceFactory(
                            getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(mSeekPosition);
            Log.d(TAG, "saving state" + mSeekPosition + "");
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mSeekPosition = mExoPlayer.getCurrentPosition();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void toPictureInPicture() {
        mControlView = mPlayerView.findViewById(R.id.exo_controller);
        mPictureInPicture = mControlView.findViewById(R.id.exo_pip);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isOnePane) {
            mPictureInPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().enterPictureInPictureMode();
                    return;
                }
            });
        } else {
            mPictureInPicture.setVisibility(View.GONE);
        }
    }
}
