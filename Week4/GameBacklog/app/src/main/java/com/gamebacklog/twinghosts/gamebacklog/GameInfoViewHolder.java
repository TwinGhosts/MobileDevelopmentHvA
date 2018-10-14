package com.gamebacklog.twinghosts.gamebacklog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class GameInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public View view;
    public TextView title;
    public TextView platform;
    public TextView status;
    public TextView notes;
    public GameItemClickListener gameItemClickListener;

    public GameInfoViewHolder(View itemView, GameItemClickListener gameItemClickListener) {
        super(itemView);
        view = itemView;
        this.title = itemView.findViewById(R.id.text_title);
        this.platform = itemView.findViewById(R.id.text_platform);
        this.status = itemView.findViewById(R.id.text_status);
        this.notes = itemView.findViewById(R.id.text_notes);
        this.gameItemClickListener = gameItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        gameItemClickListener.onItemClick(position);
    }
}
