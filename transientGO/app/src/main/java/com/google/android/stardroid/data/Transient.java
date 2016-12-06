package com.google.android.stardroid.data;

import com.google.android.stardroid.units.GeocentricCoordinates;

/**
 * Created by landon on 11/30/16.
 */

public class Transient {
    public String getLink() {
        return link;
    }

    public String getId() {
        return id;
    }


    public String getDa() {
        return da;
    }

    public String getDp() {
        return dp;
    }

    public Float getR() {
        return r;
    }

    public Float getD() {
        return d;
    }

    public Float getM(){return m;}

    public String getT(){return t;}

    public int getScore() {return score;}

    public GeocentricCoordinates getCoords(){
        return GeocentricCoordinates.getInstance(r, d);
    }

    private String link;
    private String id;
    private String da;
    private String dp;
    private String t;
    private Float r;
    private Float d;
    private Float m;
    private int score;

    public Transient(String l, String id, String da, String dp, String t, Float r, Float d, Float m, int s){
        this.link = l;
        this.id = id;
        this.da = da;
        this.dp = dp;
        this.r = r;
        this.d = d;
        this.m = m;
        this.t = t;
        this.score = s;
    }
}
