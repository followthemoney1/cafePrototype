package com.example.leaditteam.cafeprototype.helpers;

/**
 * Created by leaditteam on 20.04.17.
 */

public class LocationHelper implements java.io.Serializable  {
    String DESCRIPTION ;
    double LATITUDE;
    double LONGITUDE ;
    String TITLE ;
    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getDESCRIPTION() {

        return DESCRIPTION;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public LocationHelper(String DESCRIPTION, double LATITUDE, double LONGITUDE, String TITLE) {

        this.DESCRIPTION = DESCRIPTION;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.TITLE = TITLE;
    }


}
