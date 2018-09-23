package com.geoguess.twinghosts.geoguess;

public class GeoObject {
    private int geoImageName;
    private boolean answer;

    public GeoObject(int geoImageName, boolean answer) {
        this.geoImageName = geoImageName;
        this.answer = answer;
    }

    public int getGeoImageName() {
        return geoImageName;
    }

    public boolean isAnswer() {
        return answer;
    }
}