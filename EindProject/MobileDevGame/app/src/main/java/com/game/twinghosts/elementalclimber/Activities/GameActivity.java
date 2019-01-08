package com.game.twinghosts.elementalclimber.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.game.twinghosts.elementalclimber.Data.SoundPlayer;
import com.game.twinghosts.elementalclimber.R;

public class GameActivity extends Activity {

    private GameView gameView;
    private ConstraintLayout layout;
    private PopupWindow pausePopup;

    private MediaPlayer mediaPlayer;

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
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View pauseView = inflater.inflate(R.layout.pause_menu, null, false);
        setPopupWindowProperties(pausePopup, pauseView);

        // Create the game view and add it to the input layout
        layout = findViewById(R.id.game_constraint_layout);
        gameView = new GameView(this);
        layout.addView(gameView);

        // Play music
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.in_game_music);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        setPauseButtons(pauseView);
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
        SoundPlayer.playButtonClickSound(getApplicationContext());
        if(gameView.getGameState() == GameView.GAME_STATE_PLAYING || gameView.getGameState() == GameView.GAME_STATE_PAUSED) {
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

    private void setPauseButtons(View pauseView){
        pauseView.findViewById(R.id.button_resume).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.playButtonClickSound(getApplicationContext());
                        gameView.pause(false);
                        pausePopup.dismiss();
                    }
                }
        );

        pauseView.findViewById(R.id.button_pause_quit).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.playButtonClickSound(getApplicationContext());
                        finish();
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
