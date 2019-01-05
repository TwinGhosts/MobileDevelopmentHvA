package com.game.twinghosts.elementalclimber.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.game.twinghosts.elementalclimber.Callbacks.AsyncResult;
import com.game.twinghosts.elementalclimber.Data.DownloadWebpageTask;
import com.game.twinghosts.elementalclimber.Data.HiScore;
import com.game.twinghosts.elementalclimber.Data.HiScoreAdapter;
import com.game.twinghosts.elementalclimber.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HighScoreActivity extends Activity {

    private ListView hiScoreListView;
    private ArrayList<HiScore> hiScoreList;

    public static final String ALL_SCORES_URL = "https://spreadsheets.google.com/tq?tq=select*order+by+B+desc&key=19J040b8Nv_vfoy5z5sU6Im9brNgYqT0-ZYQc-IVtikc";
    public static final String WEB_APP_URL = "https://script.google.com/macros/s/AKfycbxH2Z-Njk4YGn1XmXQQxIekdkvncPVpxtZZOgLWZ-mFKLf0aAo/exec";

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

        hiScoreList = new ArrayList<>();
        hiScoreListView = findViewById(R.id.hi_score_listview);

        // Get the JSON response from google spreadsheet
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute(ALL_SCORES_URL);
    }

    private void processJson(JSONObject object) {
        try {
            JSONArray rows = object.getJSONArray("rows");
            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String name = columns.getJSONObject(0).getString("v");
                int score = columns.getJSONObject(1).getInt("v");

                HiScore hiScore = new HiScore(name, score);
                hiScoreList.add(hiScore);
            }

            final HiScoreAdapter adapter = new HiScoreAdapter(this, hiScoreList);
            hiScoreListView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
