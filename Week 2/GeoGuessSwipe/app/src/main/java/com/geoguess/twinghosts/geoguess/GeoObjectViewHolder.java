package com.geoguess.twinghosts.geoguess;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by marmm on 02/11/2017.
 */

public class GeoObjectViewHolder extends RecyclerView.ViewHolder {
    public ImageView locationImage;
    public View view;

    public GeoObjectViewHolder(View itemView) {
        super(itemView);
        //TODO locationImage =  itemView.findViewById(R.id.location_image_view);
        view = itemView;
    }
}


