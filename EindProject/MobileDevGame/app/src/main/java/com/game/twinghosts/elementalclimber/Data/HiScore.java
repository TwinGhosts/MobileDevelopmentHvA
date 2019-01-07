package com.game.twinghosts.elementalclimber.Data;

public class HiScore {
    private String name;
    private int score;
    private CustomDate date;

    public HiScore(String name, int score){
        this.name = name;
        this.score = score;
    }

    public void setDate(String dateString){
        date = new CustomDate(dateString);
    }

    public void setDate(int day, int month, int year){
        date = new CustomDate(day, month, year);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public CustomDate getDate(){
        return date;
    }

    public void addScore(int value){
        score += value;
    }
}
