package com.game.twinghosts.elementalclimber.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.game.twinghosts.elementalclimber.Camera.Camera;
import com.game.twinghosts.elementalclimber.Data.DataTransfer;
import com.game.twinghosts.elementalclimber.Data.GameData;
import com.game.twinghosts.elementalclimber.R;

public class GameActivity extends Activity {

    private GameView gameView;
    private ConstraintLayout layout;
    private PopupWindow pausePopup, losePopup;
    private View loseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the game run fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the control view
        setContentView(R.layout.activity_ingame);

        pausePopup = new PopupWindow(this);
        losePopup = new PopupWindow(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View pauseView = inflater.inflate(R.layout.pause_menu, null, false);
        loseView = inflater.inflate(R.layout.lose_menu, null, false);

        setPopupWindowProperties(pausePopup, pauseView);
        setPopupWindowProperties(losePopup, loseView);

        // Create the game view and add it to the input layout
        layout = findViewById(R.id.game_constraint_layout);
        gameView = new GameView(this);
        layout.addView(gameView);

        setPauseButtons(pauseView);
        setLoseButtons(loseView, this);
    }

    public void showLoseWindow(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        if(!gameView.gameIsPaused())
            gameView.pause(true);

        TextView scoreText = loseView.findViewById(R.id.text_view_score);
        scoreText.setText(GameData.hiScoreToStore.getScore());

        losePopup.showAtLocation(layout, Gravity.CENTER, 0, 0);
        losePopup.update(0, 0, size.x, size.y);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        // Show menu to ask whether the player wants to quit or resume
        if(!gameView.getGameIsLost()) {
            if (!gameView.gameIsPaused()) {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                gameView.pause(true);
                pausePopup.showAtLocation(layout, Gravity.CENTER, 0, 0);
                pausePopup.update(0, 0, size.x, size.y);
            } else {
                gameView.pause(false);
                pausePopup.dismiss();
            }
        }
    }

    private void setPopupWindowProperties(PopupWindow window, View view){
        window.setContentView(view);
        window.setAnimationStyle(R.style.PopUpAnimation);
        window.setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
    }

    private void setLoseButtons(final View loseView, final Context context){
        final Button submitButton = loseView.findViewById(R.id.button_submit_score);
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText nameText = loseView.findViewById(R.id.name_input);
                        if (!nameText.getText().toString().equals(getString(R.string.input_name_text)) && !nameText.getText().toString().equals("") && !nameText.getText().toString().equals(" ")) {
                            if (GameData.hiScoreToStore != null) {
                                DataTransfer.addScoreToSheet(context, GameData.hiScoreToStore);
                                GameData.hiScoreToStore = null;
                            }

                            submitButton.setBackgroundColor(Color.BLACK);
                            submitButton.setEnabled(false);
                        } else {
                            Toast.makeText(context, R.string.input_name_text_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        loseView.findViewById(R.id.button_main_menu).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Intent mainMenuIntent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(mainMenuIntent);
                    }
                }
        );

        loseView.findViewById(R.id.button_restart).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Intent gameIntent = new Intent(GameActivity.this, GameActivity.class);
                        startActivity(gameIntent);
                    }
                }
        );
    }

    private void setPauseButtons(View pauseView){
        pauseView.findViewById(R.id.button_resume).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameView.pause(false);
                        pausePopup.dismiss();
                    }
                }
        );

        pauseView.findViewById(R.id.button_pause_quit).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mainMenuIntent = new Intent(GameActivity.this, MainActivity.class);
                        finish();
                        startActivity(mainMenuIntent);
                    }
                }
        );
    }
}
