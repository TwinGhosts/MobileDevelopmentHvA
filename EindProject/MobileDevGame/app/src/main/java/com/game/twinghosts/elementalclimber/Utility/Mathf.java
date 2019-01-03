package com.game.twinghosts.elementalclimber.Utility;

import android.graphics.Rect;

public class Mathf {

    public static float lerp(float point1, float point2, float alpha)
    {
        return point1 + alpha * (point2 - point1);
    }

    public static boolean isOnScreen(Vector2 cameraPosition, Vector2 checkPosition, Vector2 screenSize){
        boolean xCheck = checkPosition.x > cameraPosition.x - screenSize.x && checkPosition.x < cameraPosition.x + screenSize.x;
        boolean yCheck = checkPosition.y > cameraPosition.y - screenSize.y && checkPosition.y < cameraPosition.y + screenSize.y;
        return xCheck && yCheck;
    }

    public static boolean collides(Rect r1, Rect r2) {
        return r1.intersect(r2);
    }
}
