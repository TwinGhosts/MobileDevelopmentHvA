package com.game.twinghosts.elementalclimber.Utility;

public final class Vector2 {

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(final Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 sub(final Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    public Vector2 mult(final Vector2 v) {
        return new Vector2(x * v.x, y * v.y);
    }
    public Vector2 mult(final float f) {
        return new Vector2(x * f, y * f);
    }

    public float magnitude() {
        return (float)Math.sqrt((x * x) + (y * y));
    }

    public Vector2 normalized() {
        return new Vector2(x / magnitude(), y / magnitude());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
