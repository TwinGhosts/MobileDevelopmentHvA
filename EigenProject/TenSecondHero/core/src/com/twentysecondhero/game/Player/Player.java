package com.twentysecondhero.game.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.twentysecondhero.game.Input.GameInput;
import com.twentysecondhero.game.Objects.GameObject;
import com.twentysecondhero.game.Utility.GameData;
import com.twentysecondhero.game.Utility.TileLayerName;

public class Player extends GameObject {

    public State state = State.Walking;

    private float maxVelocity = 7f;
    private float jumpVelocity = 17.5f;
    private float damping = 0.25f;
    private float stateTime = 0;
    private boolean facesRight = true;
    private boolean grounded = false;
    private Texture playerTexture;
    private float drawScale = 1.5f;

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> jumpAnimation;

    public Player(Vector2 position, float width, float height){
        super(position, width, height);

        // Define the player's graphic
        playerTexture = new Texture("mainCharacter.png");

        // Split the player's sprite-sheet into regions for animations
        TextureRegion[] regions = TextureRegion.split(playerTexture, 13, 31)[0];

        // Only use on region for now (no animation yet)
        this.width = 1 / width * regions[0].getRegionWidth();
        this.height = 1 / width * regions[0].getRegionHeight();

        this.width /= drawScale;
        this.height /= drawScale;

        // Define all of the animations
        idleAnimation = new Animation(0, regions[0]);
        jumpAnimation = new Animation(0, regions[0]);
        walkAnimation = new Animation(0.15f, regions[0], regions[0], regions[0]);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public void update(float deltaTime) throws Exception {
        // TODO break up into manageable chunks
        if (deltaTime == 0) return;

        // Reset the deltaTime
        if (deltaTime > 0.1f)
            deltaTime = 0.1f;

        // Add time the to the animation state,
        // so that it can cycle through the frames
        addStateTime(deltaTime);

        if(isAlive) {
            // Check and apply movement input
            // Jumping
            if (GameInput.isTouched(0.175f, 0.85f)) {
                jump();
            }

            // Moving left
            if (GameInput.isTouched(0, 0.175f)) {
                moveInDirection(false);
            }

            // Moving right
            if (GameInput.isTouched(0.825f, 1f)) {
                moveInDirection(true);
            }
        }

        // Apply gravity if the player is falling
        velocity.add(0, GameData.gravity);

        // Clamp the velocity to the maximum, x-axis only
        velocity.x = MathUtils.clamp(velocity.x,
                -getMaxVelocity(), getMaxVelocity());

        // If the velocity is < 1, set it to 0 and set state to Standing
        if (Math.abs(velocity.x) < 1) {
            velocity.x = 0;
            if (isGrounded()) state = Player.State.Standing;
        }

        // Multiply by delta time so we know how far the player goes in this frame
        velocity.scl(deltaTime);

        // Perform collision detection & response, on each axis, separately
        // if the player is moving right, check the tiles to the right of it's
        // right bounding box edge, otherwise check the ones to the left
        Rectangle playerRect = GameData.currentStage.getRectPool().obtain();
        playerRect.set(position.x, position.y, getWidth(), getHeight());
        int startX, startY, endX, endY;

        if (velocity.x > 0) {
            startX = endX = (int)(position.x + getWidth() + velocity.x);
        } else {
            startX = endX = (int)(position.x + velocity.x);
        }

        startY = (int)(position.y);
        endY = (int)(position.y + getHeight());
        Array<Rectangle> tiles = GameData.currentStage.getTiles(startX, startY, endX, endY, GameData.currentStage.getRectPool(), TileLayerName.Walls);
        playerRect.x += velocity.x;

        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {
                velocity.x = 0;
                break;
            }
        }

        playerRect.x = position.x;

        // When the player is moving upwards, check the tiles to the top of its
        // top bounding box edge, otherwise check the ones to the bottom
        if (velocity.y > 0) {
            startY = endY = (int)(position.y + getHeight() + velocity.y);
        } else {
            startY = endY = (int)(position.y + velocity.y);
        }

        startX = (int)(position.x);
        endX = (int)(position.x + getWidth());
        tiles = GameData.currentStage.getTiles(startX, startY, endX, endY, GameData.currentStage.getRectPool(), TileLayerName.Walls);
        playerRect.y += velocity.y;

        for (Rectangle tile : tiles) {
            if (playerRect.overlaps(tile)) {

                // Reset the player y-position
                // so it is just below/above the tile we collided with
                // this removes bouncing
                if (velocity.y > 0) {
                    position.y = tile.y - getHeight();
                } else { // If we hit the ground, mark us as grounded so we can jump
                    position.y = tile.y + tile.height;
                    setGrounded(true);
                }
                velocity.y = 0;
                break;
            }
        }

        // Check whether the player is touching the goal
        startX = endX = (int)position.x;
        startY = endY = (int)position.y;

        tiles = GameData.currentStage.getTiles(startX, startY, endX, endY, GameData.currentStage.getRectPool(), TileLayerName.Goal);
        for (Rectangle tile : tiles) {
            // if the player touched the goal tile, win!
            if (playerRect.overlaps(tile)) {
                GameData.currentStage.win();
            }
        }

        // Check whether the player touches a collectible
        tiles = GameData.currentStage.getTiles(startX, startY, endX, endY, GameData.currentStage.getRectPool(), TileLayerName.Collectibles);
        for (Rectangle tile : tiles) {
            // if the player touched the goal tile, win!
            if (playerRect.overlaps(tile)) {
                TiledMapTileLayer layer = (TiledMapTileLayer)GameData.currentStage.getMap().getLayers().get(TileLayerName.Collectibles.toString());
                layer.setCell((int)tile.x, (int)tile.y, null);
                GameData.currentStage.touchCollectible();
            }
        }

        // Check whether the player touches a death tile
        tiles = GameData.currentStage.getTiles(startX, startY, endX, endY, GameData.currentStage.getRectPool(), TileLayerName.Death);
        for (Rectangle tile : tiles) {
            // if the player touched the goal tile, win!
            if (isAlive && playerRect.overlaps(tile)) {
                isAlive = false;
            }
        }

        // Free resources
        GameData.currentStage.getRectPool().free(playerRect);

        // Un-scale the velocity by the inverse delta time and set
        // the latest position
        position.add(velocity);
        position.x = MathUtils.clamp(position.x, 0, GameData.currentStage.getWidth() - width);
        velocity.scl(1 / deltaTime);

        // Apply damping to the velocity on the x-axis so we don't
        // walk infinitely once a key was pressed
        velocity.x *= getDamping();
    }

    public void render(){
        if(isAlive) {
            // based on the player state, get the animation frame
            TextureRegion frame = null;
            switch (state) {
                case Standing:
                    frame = getIdleAnimation().getKeyFrame(getStateTime());
                    break;
                case Walking:
                    frame = getWalkAnimation().getKeyFrame(getStateTime());
                    break;
                case Jumping:
                    frame = getJumpAnimation().getKeyFrame(getStateTime());
                    break;
            }

            // draw the player, depending on the current velocity
            // on the x-axis, draw the player facing either right
            // or left
            Batch batch = GameData.currentStage.getTileMapRenderer().getBatch();
            batch.begin();
            // batch.setProjectionMatrix(GameData.camera.getCamera().combined);

            if (isFacingRight()) {
                batch.draw(frame, position.x, position.y, getWidth(), getHeight());
            } else {
                batch.draw(frame, position.x + getWidth(), position.y, -getWidth(), getHeight());
            }
            batch.end();
        }
    }

    public void jump(){
        if(isGrounded()) {
            velocity.y += getJumpVelocity();
            state = Player.State.Jumping;
            setGrounded(false);
        }
    }

    public void moveInDirection(boolean right){
        velocity.x = (right) ? getMaxVelocity() : -getMaxVelocity();
        if (isGrounded()) state = Player.State.Walking;
        setFacingDirection(right);
    }

    private Vector2 velocity = new Vector2();

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public float getDamping() {
        return damping;
    }

    public float getStateTime() {
        return stateTime;
    }

    public boolean isFacingRight() {
        return facesRight;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public Animation<TextureRegion> getJumpAnimation() {
        return jumpAnimation;
    }

    public enum State {
        Standing, Walking, Jumping
    }

    public void setFacingDirection(boolean right){
        facesRight = right;
    }

    public void setGrounded(boolean grounded){
        this.grounded = grounded;
    }

    public void addStateTime(float time){
        stateTime += time;
    }
}
