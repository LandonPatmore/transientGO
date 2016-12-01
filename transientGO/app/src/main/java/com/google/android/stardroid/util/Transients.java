package com.google.android.stardroid.util;

/**
 * Created by landon on 11/30/16.
 */

public class Transients {
    public String getAuthor() {
        return author;
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

    private String author;
    private String id;
    private String da;
    private String dp;
    private Float r;
    private Float d;

    public Transients(String a, String id, String da, String dp, Float r, Float d){
        this.author = a;
        this.id = id;
        this.da = da;
        this.dp = dp;
        this.r = r;
        this.d = d;
    }



}
