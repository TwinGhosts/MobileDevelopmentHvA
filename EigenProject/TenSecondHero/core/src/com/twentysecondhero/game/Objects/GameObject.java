package com.twentysecondhero.game.Objects;

import com.badlogic.gdx.math.Vector2;

public class GameObject {
    public Vector2 position;
    public float width, height;
    public boolean isAlive = true;

    public GameObject(Vector2 position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }
}
