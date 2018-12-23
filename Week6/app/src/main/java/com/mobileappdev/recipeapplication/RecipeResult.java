package com.mobileappdev.recipeapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResult {
    public final static int MAX_RECIPES = 3;

    @SerializedName("recipes")
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @SerializedName("recipe")
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }
}
