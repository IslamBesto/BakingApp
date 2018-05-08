package com.example.saidi.bakingapp.data.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeAPI {

    @GET("baking.json")
    Call<List<Recipe>> getAllRecipes();
}
