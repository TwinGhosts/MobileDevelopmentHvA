package com.game.twinghosts.elementalclimber.GameObjects;

import com.game.twinghosts.elementalclimber.Data.GameData;
import com.game.twinghosts.elementalclimber.Data.GameManager;
import com.game.twinghosts.elementalclimber.GameObjects.Obstacles.BaseObstacle;
import com.game.twinghosts.elementalclimber.Utility.Vector2;
import com.game.twinghosts.elementalclimber.Activities.*;

import java.util.List;

public class Player extends GameObject {
    private float gravity = 1.4f;

    private boolean isAlive = true;
    private GameView scene;

    private Vector2 velocity;

    public Player(float x, float y, Vector2 size){
        super(x, y, size);
        velocity = new Vector2(0f, 0f);
    }

    public void setScene(GameView scene){
        this.scene = scene;
    }

    public void update(GameManager gameManager){
        if(isAlive) {
            movement();
            collisionDetection(gameManager.obstacles);
        }
    }

    private void movement(){
        // Add gravity as a force
        velocity.y += gravity;

        // Finalize the movement by adding the final velocity
        position = position.add(velocity);

        clampPosition();
    }

    public void switchGravity(){
        velocity.y = 0;
        gravity = -gravity;
    }

    private void collisionDetection(List<BaseObstacle> obstacles){
        for(BaseObstacle obstacle : obstacles){
            if(obstacle.collisionRectangle.intersects(collisionRectangle.left, collisionRectangle.top, collisionRectangle.right, collisionRectangle.bottom)){
                scene.lose();
            }
        }
    }

    private void clampPosition(){
        position.y = GameData.clamp(position.y, GameData.ceilingHeight + (int)size.y/2, GameData.floorHeight - (int)size.y/2);
    }

    public boolean getIsAlive(){
        return isAlive;
    }
}
