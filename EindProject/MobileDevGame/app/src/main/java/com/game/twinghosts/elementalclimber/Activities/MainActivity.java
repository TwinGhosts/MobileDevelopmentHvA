package com.game.twinghosts.elementalclimber.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.game.twinghosts.elementalclimber.Data.DataTransfer;
import com.game.twinghosts.elementalclimber.Data.HiScores.HiScore;
import com.game.twinghosts.elementalclimber.Data.InGame.GameData;
import com.game.twinghosts.elementalclimber.Data.InGame.SoundPlayer;
import com.game.twinghosts.elementalclimber.Data.LocalStorage.AppDatabase;
import com.game.twinghosts.elementalclimber.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<HiScore> personalHiScores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main_menu);

        DataTransfer.database = AppDatabase.getInstance(this);

        new HiScoreAsyncTask(DataTransfer.TASK_GET_ALL_GAMES).execute();
        personalHiScores = DataTransfer.database.hiScoreDAO().getAllHiScoresOrderedByDesc();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> " + personalHiScores.size());

        TextView previousScoreText = findViewById(R.id.previous_score_view);
        if(!personalHiScores.isEmpty()){
            String text = getString(R.string.your_personal_best_score) + personalHiScores.get(0).getScore();
            previousScoreText.setText( text );
        } else {
            previousScoreText.setText(R.string.no_scores_yet);
        }

        Button buttonNewGame = findViewById(R.id.button_new_game);
        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.playButtonClickSound(getApplicationContext());
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        Button buttonHighScore = findViewById(R.id.button_hi_scores);
        buttonHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.playButtonClickSound(getApplicationContext());
                startActivity(new Intent(MainActivity.this, HighScoreActivity.class));
            }
        });

        Button buttonQuit = findViewById(R.id.button_quit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundPlayer.playButtonClickSound(getApplicationContext());
                finish();
                System.exit(0);
            }
        });
    }

    public static class HiScoreAsyncTask extends AsyncTask<HiScore, Void, List> {

        private int taskCode;

        public HiScoreAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(HiScore... hiScores) {
            switch (taskCode) {
                case DataTransfer.TASK_DELETE_GAMES:
                    DataTransfer.database.hiScoreDAO().deleteGames(hiScores[0]);
                    break;

                case DataTransfer.TASK_UPDATE_GAMES:
                    DataTransfer.database.hiScoreDAO().updateGames(hiScores[0]);
                    break;

                case DataTransfer.TASK_INSERT_GAMES:
                    DataTransfer.database.hiScoreDAO().insertGames(hiScores[0]);
                    break;
            }

            //To return a new list with the updated data, we get all the data from the database again.
            return DataTransfer.database.hiScoreDAO().getAllHiScoresOrderedByDesc();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
        }
    }
}
