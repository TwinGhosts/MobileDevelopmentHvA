package com.game.twinghosts.elementalclimber.Data;

public class HiScore {
    private String name;
    private int score;

    public HiScore(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int value){
        score = value;
    }

    public void addScore(int value){
        score += value;
    }
}
