package com.game.twinghosts.elementalclimber.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.twinghosts.elementalclimber.Activities.HighScoreActivity;
import com.game.twinghosts.elementalclimber.Callbacks.AsyncResult;
import com.game.twinghosts.elementalclimber.Data.DataTransfer;
import com.game.twinghosts.elementalclimber.Data.DownloadWebPageTask;
import com.game.twinghosts.elementalclimber.Data.HiScore;
import com.game.twinghosts.elementalclimber.Data.HiScoreRecycleAdapter;
import com.game.twinghosts.elementalclimber.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HiScoreFragment extends Fragment {

    private RecyclerView hiScoreListView;
    private ArrayList<HiScore> hiScoreList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hi_score, container, false);

        hiScoreList = new ArrayList<>();
        hiScoreListView =  view.findViewById(R.id.hi_score_recyclerview);

        return view;
    }

    private void processHiScoreJson(JSONObject object, int sortMode) {
        try {
            JSONArray rows = object.getJSONArray("rows");
            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String name = columns.getJSONObject(0).getString("v");
                int score = columns.getJSONObject(1).getInt("v");
                String date = columns.getJSONObject(2).getString("v");

                HiScore hiScore = new HiScore(name, score);
                hiScore.setDate(date);
                hiScoreList.add(hiScore);
            }

            // Sort the hi-scores based on the sort mode
            hiScoreList = DataTransfer.sortHiScore(hiScoreList, sortMode);

            final HiScoreRecycleAdapter adapter = new HiScoreRecycleAdapter(getContext(), hiScoreList);
            hiScoreListView.setAdapter(adapter);

            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getContext());
            hiScoreListView.setLayoutManager(layoutManager);
            hiScoreListView.setHasFixedSize(true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Downloads the .txt sheet file and processes it to turn it into hiScores
     * @param sortMode
     */
    public void sortHiScores(final int sortMode){
        // Get the JSON response from google spreadsheet
        new DownloadWebPageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processHiScoreJson(object, sortMode);
            }
        }).execute(DataTransfer.BASE_URL + DataTransfer.ALL_SCORES_DESC + DataTransfer.BASE_KEY);
    }
}
