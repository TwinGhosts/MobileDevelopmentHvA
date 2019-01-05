package com.game.twinghosts.elementalclimber.GameObjects;

import android.graphics.Bitmap;

import com.game.twinghosts.elementalclimber.Data.GameData;
import com.game.twinghosts.elementalclimber.Data.GameManager;
import com.game.twinghosts.elementalclimber.Utility.Vector2;

public class Player extends GameObject {
    private float gravity = 0.5f;
    private float currentGravity = 0f;
    private float movementSpeed = 4f;
    private float jumpHeight = 50f;

    public boolean moveLeft = false;
    public boolean moveRight = false;

    private boolean isAlive = true;

    private boolean isGrounded = false;
    private float floorHeight;

    private Vector2 velocity;
    private Vector2 screenSize;

    public Player(Bitmap bmp, Vector2 screenSize) {
        super(bmp);
        this.screenSize = screenSize;
        velocity = new Vector2(0f, 0f);

        floorHeight = screenSize.y - bmp.getHeight() - GameData.BLOCK_SIZE;
    }

    public void update(GameManager gameManager){
        movement();
    }

    private void movement(){
        // Add gravity as a force
        if(!isGrounded) {
            currentGravity += gravity;
            velocity.y += currentGravity;
        }

        // Make the player move left and right
        move();

        // Finalize the movement by adding the final velocity
        position = position.add(velocity);

        clampPosition();

        checkForGround();

        // Clear it for the next frame
        velocity.x = 0f;
        velocity.y = 0f;
    }

    private void checkForGround(){
        if(!isGrounded && position.y >= floorHeight){
            isGrounded = true;
            currentGravity = 0f;
        }
    }

    public void jump(){
        if(isGrounded) {
            isGrounded = false;
            currentGravity = 0f;
            velocity.y = -jumpHeight;
        }
    }

    public void move(){
        if(moveLeft)
            velocity.x -= movementSpeed;
        if(moveRight)
            velocity.x += movementSpeed;
    }

    private void clampPosition(){
        position.x = GameData.clamp(position.x, 0, screenSize.x);
        position.y = GameData.clamp(position.y, 0, floorHeight);
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    public void setIsAlive(boolean value){
        isAlive = value;
    }
}
