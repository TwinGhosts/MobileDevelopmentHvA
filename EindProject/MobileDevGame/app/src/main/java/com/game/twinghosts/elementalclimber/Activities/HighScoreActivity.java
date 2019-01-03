package com.game.twinghosts.elementalclimber.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.game.twinghosts.elementalclimber.R;

public class HighScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make the game run fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the control view
        setContentView(R.layout.activity_hi_score);

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
                Intent mainMenuIntent = new Intent(HighScoreActivity.this, MainActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

        Button nextScoresButton = findViewById(R.id.hi_score_next);
        nextScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(HighScoreActivity.this, MainActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });
    }
}
