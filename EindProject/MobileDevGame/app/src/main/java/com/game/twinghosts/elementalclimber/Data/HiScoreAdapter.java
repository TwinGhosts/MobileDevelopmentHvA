package com.game.twinghosts.elementalclimber.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.game.twinghosts.elementalclimber.R;

import java.util.ArrayList;
import java.util.List;

public class HiScoreAdapter extends ArrayAdapter<HiScore> {

    private Context mContext;
    private List<HiScore> moviesList = new ArrayList<>();

    public HiScoreAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<HiScore> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.hi_score_entry,parent,false);

        HiScore currentHiScore = moviesList.get(position);

        TextView nameView = listItem.findViewById(R.id.hi_score_entry_name);
        nameView.setText(currentHiScore.getName());

        TextView scoreView = listItem.findViewById(R.id.hi_score_entry_score);
        scoreView.setText("" + currentHiScore.getScore());

        return listItem;
    }
}
