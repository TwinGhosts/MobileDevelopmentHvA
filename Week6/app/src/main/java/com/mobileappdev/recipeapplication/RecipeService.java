package com.mobileappdev.recipeapplication;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit userguide at
 * https://square.github.io/retrofit/
 * More usage information at
 * https://guides.codepath.com/android/consuming-apis-with-retrofit
 */
public interface RecipeService {
    String BASE_URL = "https://www.food2fork.com/api/";
    String API_KEY = "b4d7f0bdb2ec7b17f037bcf6beefa79d";

    /**
     * Create a retrofit client.
     */
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("search?key=" + API_KEY + "&sort=r")
    Call<RecipeResult> getTopRatedRecipes();

    @GET("get?key=" + API_KEY)
    Call<RecipeResult> getIngredientsOfRecipes(@Query("rId") String rId);
}
