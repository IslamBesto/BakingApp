package com.example.saidi.bakingapp.recipedetail.customview;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saidi.bakingapp.R;
import com.example.saidi.bakingapp.data.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepView extends FrameLayout {
    @BindView(R.id.step_number)
    TextView mStepNumber;

    @BindView(R.id.short_description)
    TextView mShortDescription;

    @BindView(R.id.video_available)
    ImageView mVideoAvailable;
    private Step mStep;
    private StepClickListener mStepClickListener;
    private int mPosition;

    public StepView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public StepView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StepView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public StepView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected void init(Context context) {
        LayoutInflater.from(context).inflate(getLayoutId(), this);
        ButterKnife.bind(this);

    }

    public int getLayoutId() {
        return R.layout.step_view;
    }


    public void bind(Step step) {
        mStepNumber.setText(step.getId().toString());
        mShortDescription.setText(step.getShortDescription());
        mVideoAvailable.setVisibility(step.getVideoURL().equals("") ? INVISIBLE : VISIBLE);
        mStep = step;
    }

    public void setStepClickListener(StepClickListener stepClickListener) {
        mStepClickListener = stepClickListener;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void restoreBackgrondColor(int color) {
        setBackgroundColor(getResources().getColor(color));
    }

    @OnClick(R.id.step_container)
    public void onStepClicked() {
        mStepClickListener.onStepClicked(mStep);
        setBackgroundColor(getResources().getColor(R.color.greyLight));
    }

    public interface StepClickListener {
        void onStepClicked(Step step);
    }
}
