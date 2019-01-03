package com.game.twinghosts.elementalclimber.GameObjects.Tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.game.twinghosts.elementalclimber.Data.GameData;
import com.game.twinghosts.elementalclimber.GameObjects.GameObject;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicTile extends GameObject {

    public BasicTile(Bitmap bitmap){
        super(bitmap);
    }

    public BasicTile(Bitmap bitmap, int x, int y){
        super(bitmap, x, y);
    }

    public BasicTile(Bitmap bitmap, Vector2 position){
        super(bitmap, position);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }

    public Rect nextCollisionRect(){
        return new Rect(collisionRectangle.left, collisionRectangle.top + (int)GameData.BLOCK_SIZE, collisionRectangle.right, collisionRectangle.bottom + (int)GameData.BLOCK_SIZE);
    }
}
