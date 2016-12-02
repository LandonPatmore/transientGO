package com.google.android.stardroid.util;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by landon on 11/30/16.
 */
public class DataTransferrer {
    private static DataTransferrer instance = null;

    private DataTransferrer(){
    }

    public static DataTransferrer getInstance(){
        return instance;
    }

    private ArrayList<Transients> transientList = new ArrayList<>();

    public ArrayList<Transients> getTransients(){
        return this.transientList;
    }

    public void setTransients(ArrayList<Transients> t){
        transientList = t;
    }

    public void printAll(){
        for(Transients t : transientList){
            Log.d("DEBUG DA",t.getDa().toString());
        }
    }



}
