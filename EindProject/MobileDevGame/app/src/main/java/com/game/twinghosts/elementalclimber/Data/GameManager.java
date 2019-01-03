package com.game.twinghosts.elementalclimber.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.game.twinghosts.elementalclimber.GameObjects.Tiles.BasicTile;
import com.game.twinghosts.elementalclimber.GameObjects.Tiles.TileSingle;
import com.game.twinghosts.elementalclimber.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    public static final float SCORE_INCREMENT_PER_TICK = 1f;
    public static final float SCORE_INCREMENT_PER_BLOCK = 50f;

    private BasicTile currentTile;

    private float score = 0f;
    private int difficulty = 1;

    public List<BasicTile> tiles = new ArrayList<>();

    private Context context;

    public GameManager(Context context){
        this.context = context;
    }

    public BasicTile getRandomTile(){
        int index = new Random().nextInt(7);
        Bitmap image = null;

        switch(index) {
            default:
            case 0:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_blue);
                break;
            case 1:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_green);
                break;
            case 2:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_mint);
                break;
            case 3:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_orange);
                break;
            case 4:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_pink);
                break;
            case 5:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_red);
                break;
            case 6:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.block_yellow);
                break;
        }

        return new TileSingle(image);
    }

    public int getAmountOfBlocksPlaced(){
        return tiles.size();
    }

    public float getScore(){
        return score;
    }

    public BasicTile getCurrentTile(){
        return currentTile;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public void incrementScore(float value){
        score += value;
    }

    public void incrementDifficulty(float value){
        difficulty += value;
    }

    public void setCurrentTile(BasicTile tile){
        currentTile = tile;
    }
}
