package com.google.android.stardroid.data;

import com.google.android.stardroid.units.GeocentricCoordinates;

/**
 * Created by landon on 11/30/16.
 */

public class Transient {

    public GeocentricCoordinates getCoords(){
        return GeocentricCoordinates.getInstance(ra, decl);
    }

    public String gettId() {
        return tId;
    }

    public float getRa() {
        return ra;
    }

    public float getDecl() {
        return decl;
    }

    public float getMag() {
        return mag;
    }

    public void setMag(float mag) {
        this.mag = mag;
    }

    public String getpURL() {
        return pURL;
    }

    public String getlURL() {
        return lURL;
    }

    public String getdF() {
        return dF;
    }

    public int getScore() {
        return score;
    }

    private String tId;
    private float ra;
    private float decl;
    private float mag;
    private String pURL;
    private String lURL;
    private String dF;
    private int score;


    public Transient(String i, float r, float d, float m, String p, String l, String df, int s){
        this.tId = i;
        this.ra = r;
        this.decl = d;
        this.mag = m;
        this.pURL = p;
        this.lURL = l;
        this.dF = df;
        this.score = s;
    }
}
