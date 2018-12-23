package com.mobileappdev.recipeapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static List<Recipe> recipes = new ArrayList<>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private RecipeService service = RecipeService.retrofit.create(RecipeService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestTopRatedRecipes();
    }

    /**
     * Request top rated recipes from API.
     */
    private void requestTopRatedRecipes()
    {
        /**
         * Make an a-synchronous call by enqueing and definition of callbacks.
         */
        Call<RecipeResult> call = service.getTopRatedRecipes();
        call.enqueue(new Callback<RecipeResult>() {
            @Override
            public void onResponse(Call<RecipeResult> call, Response<RecipeResult> response) {
                RecipeResult recipeResult = response.body();
                recipes = recipeResult.getRecipes();
                if (recipes != null) {
                    if (recipes.size() > RecipeResult.MAX_RECIPES) {
                        recipes = recipes.subList(0, RecipeResult.MAX_RECIPES);
                    }

                    requestIngredientsOfTopThreeRatedRecipes();
                }
            }

            @Override
            public void onFailure(Call<RecipeResult> call, Throwable t) {
                System.out.println("error " + t.toString());
            }
        });
    }

    /**
     * Request top rated recipes from API.
     */
    private void requestIngredientsOfTopThreeRatedRecipes()
    {
        for (int i = 0; i < recipes.size(); i++) {
            /**
             * Make an a-synchronous call by enqueing and definition of callbacks.
             */
            Call<RecipeResult> call = service.getIngredientsOfRecipes(recipes.get(i).getRecipeId());
            final int finalI = i;
            call.enqueue(new Callback<RecipeResult>() {
                @Override
                public void onResponse(Call<RecipeResult> call, Response<RecipeResult> response) {
                    RecipeResult recipeResult = response.body();
                    recipes.set(finalI, recipeResult.getRecipe());
                    // Create the adapter that will return a fragment for each of the three
                    // primary sections of the activity.
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                    // Set up the ViewPager with the sections adapter.
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                }

                @Override
                public void onFailure(Call<RecipeResult> call, Throwable t) {
                    System.out.println("error " + t.toString());
                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return MainActivity.PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return recipes.size();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new  PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ImageView imageView = rootView.findViewById(R.id.imageView);
            Picasso.get().load(
                    MainActivity.recipes.get(getArguments().getInt(ARG_SECTION_NUMBER)).getImageUrl()
            ).into(imageView);

            TextView title = rootView.findViewById(R.id.title);
            title.setText(
                    MainActivity.recipes.get(getArguments().getInt(ARG_SECTION_NUMBER)).getTitle()
            );

            TextView ingredients = rootView.findViewById(R.id.ingredients);
            ingredients.setText("Ingredients :\n");
            List<String> ingredientsList =
                    MainActivity.recipes.get(
                            getArguments().getInt(ARG_SECTION_NUMBER)
                    ).getIngredients();
            if (ingredientsList != null) {
                for (String ingredient : ingredientsList) {
                    ingredients.append(String.format("- %s \n", ingredient));
                }
            }

            return rootView;
        }
    }
}
