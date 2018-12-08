package com.twentysecondhero.game.Utility;

import com.twentysecondhero.game.Camera.CameraShake;
import com.twentysecondhero.game.Camera.GameCamera;
import com.twentysecondhero.game.Player.Player;
import com.twentysecondhero.game.Stage.GameStage;

public class GameData {
    public static GameStage currentStage;
    public static GameCamera camera;
    public static float blockSize = 16f;
    public static float gravity = -1f;
    public static long stageTime = 10L;
    public static Player player;
    public static CameraShake cameraShake;

    /**
     * Method which returns a value in seconds times a hundred to compare with divided nano values
     * from the nanoToHundreds(long value) method
     * @return
     */
    public static long secondsToHundreds(long value){
        return value * 100;
    }

    /**
     * Method which returns a second as hundreds from nano values to be compared with seconds
     * from the secondsToHundreds(long value) method
     * @return
     */
    public static long nanoToHundreds(long value){
        return value / 100000000000000L;
    }
}
