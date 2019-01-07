package com.game.twinghosts.elementalclimber.Data;

import java.util.Calendar;
import java.util.Date;

public class CustomDate {
    private int day, month, year;

    public CustomDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public CustomDate(String dateString){
        dateString = dateString.replace("Date(" ,"");
        dateString = dateString.replace(")" ,"");
        String[] splitResult = dateString.split(",");

        day = Integer.parseInt(splitResult[2]);
        month = Integer.parseInt(splitResult[1] + 1);
        year = Integer.parseInt(splitResult[0]);
    }

    public CustomDate(){
        Calendar calender = Calendar.getInstance();
        day = calender.get(Calendar.DAY_OF_MONTH);
        month = calender.get(Calendar.MONTH) + 1;
        year = calender.get(Calendar.YEAR);
    }

    @Override
    public String toString(){
        return "" + day + "/" + month + "/" + year;
    }

    public boolean sameAs(CustomDate other){
        System.out.println(toString() + " ||| " + other.toString());
        return day == other.getDay() && month == other.getMonth() && year == other.getYear();
    }

    public int getDay(){
        return day;
    }

    public int getMonth(){
        return month;
    }

    public int getYear(){
        return year;
    }
}
