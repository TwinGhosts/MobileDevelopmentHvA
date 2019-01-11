package com.game.twinghosts.elementalclimber.GameObjects.Obstacles;

import com.game.twinghosts.elementalclimber.Data.InGame.GameData;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class MovingBlockObstacle extends BaseObstacle {

    private boolean reachedTop = false;
    private int speed = 0;

    public MovingBlockObstacle(float x, float y, Vector2 size, int color, int speed){ super(x, y, size, color); this.speed = speed; }

    @Override
    public void executeBehaviour() {
        int verticalMovementSpeed = (reachedTop) ? speed : -speed;
        position.y = GameData.clamp(position.y + verticalMovementSpeed, GameData.ceilingHeight, GameData.floorHeight);
        if(position.y <= GameData.ceilingHeight + size.y/2 || position.y >= GameData.floorHeight - size.y/2){
            reachedTop = !reachedTop;
        }
    }
}
