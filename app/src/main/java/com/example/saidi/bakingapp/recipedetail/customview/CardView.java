package com.example.saidi.bakingapp.recipedetail.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saidi.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CardView extends android.support.v7.widget.CardView {
    @BindView(R.id.title_card)
    TextView mTitleCard;

    @BindView(R.id.card_container)
    LinearLayout mCaontainerCard;

    public CardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    protected void init(Context context) {
        LayoutInflater.from(context).inflate(getLayoutId(), this);
        ButterKnife.bind(this);
        mTitleCard.setText("Ingredient");
    }

    public int getLayoutId() {
        return R.layout.card_view;
    }

    public View getContainer() {
        return mCaontainerCard;
    }

    public void setTitle(String title) {
        mTitleCard.setText(title);
    }
}
