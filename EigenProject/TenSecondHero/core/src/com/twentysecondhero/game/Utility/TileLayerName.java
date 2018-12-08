package com.twentysecondhero.game.Utility;

public enum TileLayerName {
    Walls ("walls"),
    Goal ("goal"),
    Collectibles ("collectibles"),
    Background ("background"),
    Player ("player"),
    Death ("death");

    private final String text;

    TileLayerName(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
