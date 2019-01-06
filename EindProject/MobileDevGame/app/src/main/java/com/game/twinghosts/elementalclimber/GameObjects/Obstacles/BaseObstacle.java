package com.game.twinghosts.elementalclimber.GameObjects.Obstacles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.twinghosts.elementalclimber.Data.GameData;
import com.game.twinghosts.elementalclimber.GameObjects.GameObject;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

public abstract class BaseObstacle extends GameObject {

    public BaseObstacle(float x, float y, Vector2 size){ super(x, y, size); }

    public BaseObstacle(Bitmap bitmap){
        super(bitmap);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }
}
