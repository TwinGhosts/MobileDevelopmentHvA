package com.game.twinghosts.elementalclimber.Camera;

import com.game.twinghosts.elementalclimber.GameObjects.GameObject;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class Camera {
    public Vector2 position;
    private GameObject target;
    private float chaseSpeed = 4f;

    public Camera(Vector2 position){
        this.position = position;
    }

    public void setTarget(GameObject target){
        this.target = target;
    }

    public void chaseTarget(){
        Vector2 directionVector = target.position.sub(position).mult(chaseSpeed);
        position.add(directionVector);
    }
}
