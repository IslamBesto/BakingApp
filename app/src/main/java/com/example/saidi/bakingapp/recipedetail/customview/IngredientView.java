package com.example.saidi.bakingapp.recipedetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.saidi.bakingapp.R;
import com.example.saidi.bakingapp.data.model.Ingredient;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientView extends FrameLayout {
    @BindView(R.id.ingredient_title)
    TextView mIngredientTitle;

    @BindView(R.id.ingredient_quantity)
    TextView mIngredientQuantity;

    public IngredientView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public IngredientView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IngredientView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public IngredientView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    protected void init(Context context) {
        LayoutInflater.from(context).inflate(getLayoutId(), this);
        ButterKnife.bind(this);
    }

    public int getLayoutId() {
        return R.layout.ingredient_view;
    }

    /**
     * Set Ingredient infos
     *
     * @param ingredient
     */
    public void bind(Ingredient ingredient) {
        mIngredientTitle.setText(ingredient.getIngredient());
        String quantity = new StringBuilder().append(ingredient.getQuantity()).append(ingredient.getMeasure()).toString();
        mIngredientQuantity.setText(quantity);
    }
}
