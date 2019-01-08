package com.game.twinghosts.elementalclimber.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.game.twinghosts.elementalclimber.GameObjects.Obstacles.BaseObstacle;
import com.game.twinghosts.elementalclimber.GameObjects.Obstacles.BlockObstacle;
import com.game.twinghosts.elementalclimber.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private int speed = 8;

    public List<BaseObstacle> obstacles = new ArrayList<>();

    private Context context;

    public GameManager(Context context){
        this.context = context;
    }

    public BaseObstacle getRandomTile(){
        int index = new Random().nextInt(7);
        Bitmap image;

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

        return null;
    }

    public void updateObstacles(){
        if(!obstacles.isEmpty()) {
            for (int i = obstacles.size() - 1; i >= 0; i--) {
                BaseObstacle obstacle = obstacles.get(i);
                obstacle.position.x -= speed;
                obstacle.executeBehaviour();

                if (obstacle.position.x < 0) {
                    obstacles.remove(obstacle);
                }
            }
        }
    }

    public int getSpeed(){
        return speed;
    }

    public void incrementSpeed(int value){
        speed += value;
    }
}
