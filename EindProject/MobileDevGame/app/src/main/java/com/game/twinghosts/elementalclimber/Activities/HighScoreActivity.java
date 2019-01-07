package com.game.twinghosts.elementalclimber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.game.twinghosts.elementalclimber.Data.DataTransfer;
import com.game.twinghosts.elementalclimber.Fragments.HiScoreFragment;
import com.game.twinghosts.elementalclimber.R;

public class HighScoreActivity extends FragmentActivity {

    private int currentHiScoreIndex = 0;
    private final int maxHiScoreIndex = 2;
    private TextView categoryText;

    private HiScoreFragment hiScoreFragmentAll;
    private HiScoreFragment hiScoreFragmentToday;
    private HiScoreFragment hiScoreFragmentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make the game run fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the control view
        setContentView(R.layout.activity_hi_score);

        categoryText = findViewById(R.id.hi_score_category);

        changeScoreFragment(0);

        Button backButton = findViewById(R.id.button_back_hi_score);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(HighScoreActivity.this, MainActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

        Button previousScoresButton = findViewById(R.id.hi_score_previous);
        previousScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeScoreFragment(currentHiScoreIndex-1);
            }
        });

        Button nextScoresButton = findViewById(R.id.hi_score_next);
        nextScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeScoreFragment(currentHiScoreIndex+1);
            }
        });
    }

    /**
     * Changes the HiScore RecycleList fragment to a different one with a different SQL Statement
     * @param index defines which statement gets chosen
     *              1: Today's Scores DESC
     *              2: This Month's Scores DESC
     *              3: All Scores DESC
     */
    private void changeScoreFragment(int index){
        // Make sure the index falls within a valid range, wrap
        if(index > maxHiScoreIndex) index = 0;
        if(index < 0) index = maxHiScoreIndex;
        currentHiScoreIndex = index;

        // This cant be done unless connected to the internet
        if(!DataTransfer.isConnectedToInternet(this)){
            Toast.makeText(this, R.string.enable_internet, Toast.LENGTH_SHORT).show();
            return;
        }

        // Set up the changing of fragments
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HiScoreFragment hiScoreFragment;

        // Get the correct hiScore sorting
        int sortMode;
        switch(index){ // TODO remove magic number stuff
            default:
            case 0:
                sortMode = DataTransfer.SORTING_TODAY;
                categoryText.setText(R.string.hi_score_today);
                if(hiScoreFragmentToday == null) hiScoreFragmentToday = new HiScoreFragment();
                hiScoreFragment = hiScoreFragmentToday;
                break;

            case 1:
                sortMode = DataTransfer.SORTING_MONTH;
                categoryText.setText(R.string.hi_score_month);
                if(hiScoreFragmentMonth == null) hiScoreFragmentMonth = new HiScoreFragment();
                hiScoreFragment = hiScoreFragmentMonth;
                break;

            case 2:
                sortMode = DataTransfer.SORTING_ALL;
                categoryText.setText(R.string.hi_score_all_time);
                if(hiScoreFragmentAll == null) hiScoreFragmentAll = new HiScoreFragment();
                hiScoreFragment = hiScoreFragmentAll;
                break;
        }

        // Add the arguments to the fragment
        hiScoreFragment.sortHiScores(sortMode);

        // Replace the current layout with the fragment and commit
        fragmentTransaction.replace(R.id.contraint_fragment, hiScoreFragment);
        fragmentTransaction.commit();
    }
}
