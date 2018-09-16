package com.higherlower.twinghosts.higher_lower;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class HigherLowerActivity extends AppCompatActivity {
    private TextView highScoreText, currentScoreText;
    private ImageView diceImageView;
    private ListView previousRollList;
    private ArrayAdapter previousRollAdapter;
    private ArrayList<Drawable> diceImageViewList;

    private int previousRoll, currentRoll, score, hiScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_higher_lower);

        diceImageView = findViewById(R.id.dice_image);
        previousRollList = findViewById(R.id.roll_list);
        highScoreText = findViewById(R.id.high_score);
        currentScoreText = findViewById(R.id.current_score);
        previousRollAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        previousRoll = hiScore = score = currentRoll = 0;
        initDiceImageViewList();
    }

    private void rollDice(){
        previousRoll = currentRoll;
        currentRoll = randomDiceRoll();
        previousRollAdapter.add(currentRoll);
        updateUI();
    }

    private int randomDiceRoll(){
        Random rand = new Random();
        int roll = rand.nextInt(6);

        while(roll == currentRoll-1){
            roll = rand.nextInt(6);
        }

        return roll + 1;
    }

    /**
     * When the user guesses that the roll will be higher than the last
     * @param view
     */
    public void onHigherClick(View view){
        rollDice();

        if(currentRoll > previousRoll){
            OnGuess(true, view);
        } else {
            OnGuess(false, view);
        }
    }

    /**
     * When the user guesses that the roll will be lower than the last
     * @param view
     */
    public void onLowerClick(View view) {
        rollDice();

        if(currentRoll < previousRoll){
            OnGuess(true, view);
        } else {
            OnGuess(false, view);
        }
    }

    /**
     * Handles whether the guess is correct or incorrect
     * @param isGoodGuess
     * @param view
     */
    private void OnGuess(boolean isGoodGuess, View view){
        if(isGoodGuess) {
            score++;
            Snackbar.make(view, "You guessed correctly! +1 score", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else {
            previousRollAdapter.clear();

            // Show a message based on whether the user beat the hiscore or not and change the hiscore when the user did
            if(score > hiScore){
                hiScore = score;
                Snackbar.make(view, "You guessed incorrectly, but you beat your hiScore!", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            } else {
                Snackbar.make(view, "You guessed incorrectly and You didn't beat your hiScore", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
            score = 0;
        }
        updateUI();
    }

    private void initDiceImageViewList(){
        diceImageViewList = new ArrayList<>();
        diceImageViewList.add(getResources().getDrawable( R.drawable.d1));
        diceImageViewList.add(getResources().getDrawable( R.drawable.d2));
        diceImageViewList.add(getResources().getDrawable( R.drawable.d3));
        diceImageViewList.add(getResources().getDrawable( R.drawable.d4));
        diceImageViewList.add(getResources().getDrawable( R.drawable.d5));
        diceImageViewList.add(getResources().getDrawable( R.drawable.d6));
    }

    private void updateUI(){
        currentScoreText.setText("Current Score: " + score);
        highScoreText.setText("High-Score: " + hiScore);
        diceImageView.setImageDrawable(diceImageViewList.get(currentRoll-1));
        previousRollList.setAdapter(previousRollAdapter);
        previousRollList.setSelection(previousRollAdapter.getCount() - 1);
        previousRollAdapter.notifyDataSetChanged();
    }
}
