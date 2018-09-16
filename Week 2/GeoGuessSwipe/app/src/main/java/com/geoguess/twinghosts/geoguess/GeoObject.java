package com.geoguess.twinghosts.geoguess;

public class GeoObject {
    private boolean isInEurope;
    private int imageIndex;

    public boolean getIsInEurope() { return isInEurope; }
    public int getImageIndex() { return imageIndex; }

    public GeoObject(boolean isInEurope, int imageIndex){
        this.isInEurope = isInEurope;
        this.imageIndex = imageIndex;
    }

    public static final int[] PRE_DEFINED_GEO_OBJECT_IMAGE_IDS = {
            R.drawable.img1_yes_denmark,
            R.drawable.img2_no_canada,
            R.drawable.img3_no_bangladesh,
            R.drawable.img4_yes_kazachstan,
            R.drawable.img5_no_colombia,
            R.drawable.img6_yes_poland,
            R.drawable.img7_yes_malta,
            R.drawable.img8_no_thailand,
    };
}
