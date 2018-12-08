package com.glass.plat4mation.assignment5;

public class Fact {

    private String factText;
    private int factNumber;

    public Fact(int factNumber, String factText){
        this.factText = factText;
        this.factNumber = factNumber;
    }

    public String getText(){
        return factText;
    }

    public int getNumber(){
        return factNumber;
    }
}
