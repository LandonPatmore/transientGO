package com.google.android.stardroid.util;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by landon on 11/30/16.
 */
public class DataTransferrer {
    private static DataTransferrer instance = null;

    private ArrayList<Transients> transientList;

    private DataTransferrer(){
        transientList = new ArrayList<>();
    }

    public static synchronized DataTransferrer getInstance(){
        if(instance == null) {instance = new DataTransferrer();}
        return instance;
    }

    public ArrayList<Transients> getTransients(){return this.transientList;}

    public void setTransients(ArrayList<Transients> t){transientList = t;}

    public void printAll(){
        for(Transients t : transientList){
            Log.d("DEBUG DA",t.getDa().toString());
        }
    }



}
