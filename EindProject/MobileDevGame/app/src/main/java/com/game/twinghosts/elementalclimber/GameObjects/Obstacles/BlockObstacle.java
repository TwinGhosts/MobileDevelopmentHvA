package com.game.twinghosts.elementalclimber.GameObjects.Obstacles;

import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class BlockObstacle extends BaseObstacle {

    public BlockObstacle(float x, float y, Vector2 size, int color){ super(x, y, size, color); }

    @Override
    public void executeBehaviour() {
        // Nothing
    }
}
