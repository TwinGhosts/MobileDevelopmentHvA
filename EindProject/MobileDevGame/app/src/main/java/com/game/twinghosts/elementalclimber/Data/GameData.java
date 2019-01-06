package com.game.twinghosts.elementalclimber.Data;

public class GameData {
    public static final float BLOCK_SIZE = 64;
    public static HiScore hiScoreToStore;
    public static int ceilingHeight = 0;
    public static int floorHeight = 0;
    public static final int SCORE_INCREMENT_PER_TICK = 10;
    public static final int SCORE_INCREMENT_PER_BLOCK = 50;
    public static final int SCORE_INCREMENT_PER_DIFFICULTY = 400;
    public static int difficulty = 1;
    public static final int DIFFICULTY_INTERVAL_REDUCTION = 10;

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
