package com.game.twinghosts.elementalclimber.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.twinghosts.elementalclimber.R;

import java.util.List;

public class HiScoreRecycleAdapter extends RecyclerView.Adapter<HiScoreViewHolder> {
    private Context context;
    private List<HiScore> hiScoreList;

    public HiScoreRecycleAdapter(Context context, List<HiScore> listGeoObject) {
        this.context = context;
        this.hiScoreList = listGeoObject;
    }

    @NonNull
    @Override
    public HiScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hi_score_entry, parent, false);
        return new HiScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HiScoreViewHolder holder, final int position) {
        final HiScore hiScore = hiScoreList.get(position);
        holder.scoreView.setText("" + hiScore.getScore());
        holder.nameView.setText(hiScore.getName());
    }

    @Override
    public int getItemCount() {
        return hiScoreList.size();
    }
}