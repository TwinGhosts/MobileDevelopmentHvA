package com.game.twinghosts.elementalclimber.GameObjects.Obstacles;

import android.graphics.Canvas;
import android.graphics.Color;

import com.game.twinghosts.elementalclimber.GameObjects.GameObject;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

public abstract class BaseObstacle extends GameObject {

    protected int color;

    public BaseObstacle(float x, float y, Vector2 size, int color) {
        super(x, y, size);
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }

    public abstract void executeBehaviour();

    public int getColor(){
        return color;
    }
}
