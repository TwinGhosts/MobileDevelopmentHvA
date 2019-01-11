package com.game.twinghosts.elementalclimber.Data.HiScores;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.game.twinghosts.elementalclimber.Data.CustomDate;

import java.io.Serializable;

@Entity(tableName = "hiScore")
public class HiScore implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "score")
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

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
