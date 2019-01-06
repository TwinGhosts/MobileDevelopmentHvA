package com.game.twinghosts.elementalclimber.GameObjects.Obstacles;

import android.graphics.Bitmap;

import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class BlockObstacle extends BaseObstacle {

    public BlockObstacle(float x, float y, Vector2 size){ super(x, y, size); }

    public BlockObstacle(Bitmap bitmap){
        super(bitmap);
    }
}
