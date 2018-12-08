package com.twentysecondhero.game.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.twentysecondhero.game.Objects.GameObject;
import com.twentysecondhero.game.Utility.GameData;

public class GameCamera {

    private OrthographicCamera camera;
    private GameObject target;
    private float smoothing = 0.05f;

    public GameCamera(){
        GameData.camera = this;

        // create an orthographic camera, shows us 30x20 units of the world
        camera = new OrthographicCamera(Gdx.graphics.getWidth()/GameData.blockSize, Gdx.graphics.getHeight()/GameData.blockSize);
        camera.setToOrtho(false, 16, 10);
        camera.update();
    }

    public void update(){
        // let the camera follow the player
        if(target != null) {
            camera.position.lerp(new Vector3(target.position, 0f), smoothing);
            camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth/2f, GameData.currentStage.getWidth() - camera.viewportWidth/2f);
            camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight/2f, GameData.currentStage.getHeight() - camera.viewportHeight/2f);
            camera.update();
        }
    }

    public void setTarget(GameObject target){
        this.target = target;
    }

    public OrthographicCamera getCamera(){
        return camera;
    }
}
