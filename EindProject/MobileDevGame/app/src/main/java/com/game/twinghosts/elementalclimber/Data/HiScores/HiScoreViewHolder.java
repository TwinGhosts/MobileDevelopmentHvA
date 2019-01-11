package com.game.twinghosts.elementalclimber.Data.HiScores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.game.twinghosts.elementalclimber.R;

public class HiScoreViewHolder extends RecyclerView.ViewHolder {
    public TextView nameView;
    public TextView scoreView;
    public View view;

    public HiScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.hi_score_entry_name);
        scoreView = itemView.findViewById(R.id.hi_score_entry_score);
        view = itemView;
    }
}
