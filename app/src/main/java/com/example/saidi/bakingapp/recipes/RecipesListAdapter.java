package com.example.saidi.bakingapp.recipes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saidi.bakingapp.R;
import com.example.saidi.bakingapp.Utils;
import com.example.saidi.bakingapp.data.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder> {

    private Context mContext;
    private RecipeListClickListener mRecipeListClickListener;
    private List<Recipe> mRecipes;

    public RecipesListAdapter(List<Recipe> recipeList,
            RecipeListClickListener recipeListClickListener) {
        mRecipeListClickListener = recipeListClickListener;
        mRecipes = recipeList;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View recipeView = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent,
                false);
        return new RecipesViewHolder(recipeView, mRecipeListClickListener);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        RecipesViewHolder recipesViewHolder = holder;
        recipesViewHolder.bind(mRecipes.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_image)
        ImageView mRecipeImage;

        @BindView(R.id.recipe_title)
        TextView mRecipeTitle;

        @BindView(R.id.serving_number)
        TextView mServingNumber;

        private Recipe mRecipe;

        private RecipeListClickListener mRecipeListClickListener;

        public RecipesViewHolder(View itemView, RecipeListClickListener recipeListClickListener) {
            super(itemView);
            mRecipeListClickListener = recipeListClickListener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Recipe recipe) {
            mRecipeTitle.setText(recipe.getName());
            mServingNumber.setText(mContext.getString(R.string.servings, recipe.getServings().toString()));
            mRecipeImage.setImageResource(Utils.getRecipeDrawable(recipe.getName()));
            mRecipe = recipe;
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mRecipeListClickListener.onRecipeClicked(v, getAdapterPosition(), mRecipe);
            }
        }
    }

    public interface RecipeListClickListener {

        void onRecipeClicked(View view, int position, Recipe recipe);
    }
}
