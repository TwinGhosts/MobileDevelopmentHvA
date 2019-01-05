package com.game.twinghosts.elementalclimber.Data;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.game.twinghosts.elementalclimber.GameObjects.Tiles.BasicTile;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class GameData {
    public static float BLOCK_SIZE;
    public static final int DIFFICULTY_INCREASE_BLOCKS = 10;
    public static final int STAGE_WIDTH = 10;
    public static HiScore hiScoreToStore;

    public static void setBlockSizeBasedOnResolution(float stageSizeX){
        BLOCK_SIZE = stageSizeX / STAGE_WIDTH;
    }

    public static Drawable GetImage(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
