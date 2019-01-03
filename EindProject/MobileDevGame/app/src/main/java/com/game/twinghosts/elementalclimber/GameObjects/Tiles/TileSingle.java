package com.game.twinghosts.elementalclimber.GameObjects.Tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class TileSingle extends BasicTile {

    public TileSingle(Bitmap bitmap){
        super(bitmap);
    }

    public TileSingle(Bitmap bitmap, int x, int y){
        super(bitmap, x, y);
    }

    public TileSingle(Bitmap bitmap, Vector2 position){
        super(bitmap, position);
    }
}
