package com.example.marmm.placesofinterest;

/**
 * Created by marmm on 02/11/2017.
 */


public class GeoObject {
    private String mGeoName;
    private int mGeoImageName;
    public GeoObject(String mGeoName, int mGeoImageName) {
        this.mGeoName = mGeoName;
        this.mGeoImageName = mGeoImageName;
    }

    public String getmGeoName() {
        return mGeoName;
    }
    public void setmGeoName(String mGeoName) {
        this.mGeoName = mGeoName;
    }
    public int getmGeoImageName() {
        return mGeoImageName;
    }
    public void setmGeoImageName(int mGeoImageName) {
        this.mGeoImageName = mGeoImageName;
    }
    public static final String[] PRE_DEFINED_GEO_OBJECT_NAMES = {
            "Amsterdam Dam",
            "Amsterdam Weesperplein",
            "Rotterdam Euromast",
            "Den Haag Binnenhof",
            "Utrecht Dom",
            "Groningen Martinitoren",
            "Maastricht Vrijthof",
            "New York Vrijheidsbeeld",
            "San Francisco Golden Gate",
            "Yellowstone Old Faithful",
            "Yosemite Half Dome",
            "Washington White House",
            "Ottawa Parliament Hill",
            "Londen Tower Bridge",
            "Brussel Manneken Pis",
            "Berlijn Reichstag",
            "Parijs Eiffeltoren",
            "Barcelona Sagrada Familia",
            "Rome Colosseum",
            "Pompeii",
            "Kopenhagen",
            "Oslo",
            "Stockholm",
            "Helsinki",
            "Moskou Rode Plein",
            "Beijing Verboden Stad",
            "Kaapstad Tafelberg",
            "Rio de Janeiro Copacabana",
            "Sydney Opera",
            "Hawaii Honolulu",
            "Alaska Denali"
    };


    public static final int[] PRE_DEFINED_GEO_OBJECT_IMAGE_IDS = {

            R.drawable.amsterdam_dam,
            R.drawable.amsterdam_weesperplein,
            R.drawable.rotterdam_euromast,
            R.drawable.den_haag_binnenhof,
            R.drawable.utrecht_dom,
            R.drawable.groningen_martinitoren,
            R.drawable.maastricht_vrijthof,
            R.drawable.new_york_vrijheidsbeeld,
            R.drawable.san_francisco_golden_gate,
            R.drawable.yellowstone_old_faithful,
            R.drawable.yosemite_half_dome,
            R.drawable.washington_white_house,
            R.drawable.ottawa_parliament_hill,
            R.drawable.london_tower_bridge,
            R.drawable.brussel_manneken_pis,
            R.drawable.berlijn_reichstag,
            R.drawable.parijs_eiffeltoren,
            R.drawable.barcelona_sagrada_familia,
            R.drawable.rome_colosseum,
            R.drawable.pompeii,
            R.drawable.kopenhagen,
            R.drawable.oslo,
            R.drawable.stockholm,
            R.drawable.helsinki,
            R.drawable.moskou_rode_plein,
            R.drawable.beijing_verboden_stad,
            R.drawable.kaapstad_tafelberg,
            R.drawable.rio_de_janeiro_copacabana,
            R.drawable.sydney_opera,
            R.drawable.hawaii,
            R.drawable.alaska_denali
    };

}