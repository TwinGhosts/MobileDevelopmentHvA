package com.gamebacklog.twinghosts.gamebacklog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GameInfoAdapter extends RecyclerView.Adapter<GameInfoViewHolder> {

    private Context context;
    private List<GameInfo> gameInfoList;
    private final GameItemClickListener gameItemClickListener;

    public GameInfoAdapter(Context context, List<GameInfo> gameInfoList, GameItemClickListener gameItemClickListener) {
        this.context = context;
        this.gameInfoList = gameInfoList;
        this.gameItemClickListener = gameItemClickListener;
    }

    @NonNull
    @Override
    public GameInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_info_layout, parent, false);
        return new GameInfoViewHolder(view, gameItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameInfoViewHolder holder, int position) {
        final GameInfo gameInfo = gameInfoList.get(position);

        holder.title.setText(gameInfo.getTitle());
        holder.platform.setText(gameInfo.getPlatform());
        holder.status.setText(gameInfo.getStatus());
        holder.notes.setText(gameInfo.getNotes());
    }

    @Override
    public int getItemCount() {
        return gameInfoList.size();
    }

    public void swapList(List<GameInfo> newList) {
        gameInfoList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        gameInfoList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, gameInfoList.size());
    }
}
