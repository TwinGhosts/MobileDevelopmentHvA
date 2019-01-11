package com.game.twinghosts.elementalclimber.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.game.twinghosts.elementalclimber.Data.DataTransfer;
import com.game.twinghosts.elementalclimber.Data.InGame.GameData;
import com.game.twinghosts.elementalclimber.Data.InGame.SoundPlayer;
import com.game.twinghosts.elementalclimber.R;

public class GameLostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the game run fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the control view
        setContentView(R.layout.lose_menu);

        TextView scoreView = findViewById(R.id.text_view_score);
        scoreView.setText(scoreView.getText() + "" + GameData.hiScoreToStore.getScore());

        setLoseButtons(this);
    }

    private void setLoseButtons(final Context context){
        final Button submitButton = findViewById(R.id.button_submit_score);
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.playButtonClickSound(context);
                        // This cant be done unless connected to the internet
                        if(!DataTransfer.isConnectedToInternet(getBaseContext())){
                            Toast.makeText(getBaseContext(), R.string.enable_internet, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        EditText nameText = findViewById(R.id.name_input);
                        if (!nameText.getText().toString().equals(getString(R.string.input_name_text)) && !nameText.getText().toString().equals("") && !nameText.getText().toString().equals(" ")) {
                            if (GameData.hiScoreToStore != null) {
                                GameData.hiScoreToStore.setName(nameText.getText().toString());
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

        findViewById(R.id.button_main_menu).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.playButtonClickSound(context);
                        finish();
                    }
                }
        );

        findViewById(R.id.button_restart).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SoundPlayer.playButtonClickSound(context);
                        finish();
                        Intent gameIntent = new Intent(GameLostActivity.this, GameActivity.class);
                        startActivity(gameIntent);
                    }
                }
        );
    }
}
