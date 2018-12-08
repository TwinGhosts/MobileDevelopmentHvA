package com.twentysecondhero.game.Camera;
import java.util.Random;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.twentysecondhero.game.Utility.GameData;

public class CameraShake {
    private float[] samples;
    private Random rand = new Random();
    private float internalTimer = 0;
    private float shakeDuration = 0;

    private int duration = 2;
    private int frequency = 2;
    private float amplitude = 2;
    private boolean falloff = true;

    int sampleCount;

    public CameraShake(){
        GameData.cameraShake = this;
        sampleCount = duration * frequency;
        samples = new float[sampleCount];
        for (int i =0; i < sampleCount; i++){
            samples[i] = rand.nextFloat() * 2f - 1f;
        }
    }

    /**
     * Called every frame will shake the camera if it has a shake duration
     * @param dt Gdx.graphics.getDeltaTime() or your dt in seconds
     * @param camera your camera
     * @param center Where the camera should stay centered on
     */
    public void update(float dt, Camera camera, Vector2 center){
        internalTimer += dt;
        if (internalTimer > duration) internalTimer -= duration;
        if (shakeDuration > 0){
            shakeDuration -= dt;
            float shakeTime = (internalTimer * frequency);
            int first = (int)shakeTime;
            int second = (first + 1)%sampleCount;
            int third = (first + 2)%sampleCount;
            float deltaT = shakeTime - (int)shakeTime;
            float deltaX = samples[first] * deltaT + samples[second] * ( 1f - deltaT);
            float deltaY = samples[second] * deltaT + samples[third] * ( 1f - deltaT);

            camera.position.x = center.x + deltaX * amplitude * (falloff ? Math.min(shakeDuration, 1f) : 1f);
            camera.position.y = center.y + deltaY * amplitude * (falloff ? Math.min(shakeDuration, 1f) : 1f);
            camera.update();
        }
    }

    /**
     * Will make the camera shake for the duration passed in in seconds
     * @param d duration of the shake in seconds
     */
    public void shake(float d){
        shakeDuration = d;
    }
}
