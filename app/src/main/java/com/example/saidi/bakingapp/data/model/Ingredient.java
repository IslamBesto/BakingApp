package com.example.saidi.bakingapp.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by saidi on 08/05/2018.
 */

class Ingredient {

    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
