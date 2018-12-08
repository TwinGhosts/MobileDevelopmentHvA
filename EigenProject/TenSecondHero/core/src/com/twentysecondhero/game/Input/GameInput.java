package com.twentysecondhero.game.Input;

import com.badlogic.gdx.Gdx;

public class GameInput {

    public static boolean isTouched(float left, float right){
        // Check for touch inputs between left and right
        // left/right are given between 0 (left edge of the screen) and 1 (right edge of the screen)
        for (int i = 0; i < 2; i++) {
            float x = Gdx.input.getX(i) / (float)Gdx.graphics.getWidth();
            if (Gdx.input.isTouched(i) && (x >= left && x <= right)) {
                return true;
            }
        }
        return false;
    }
}
