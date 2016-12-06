package com.google.android.stardroid.data;

import java.util.ArrayList;

/**
 * Created by Zackary on 12/1/2016.
 */
public enum TransientData {
    INSTANCE;

    private ArrayList<Transient> aTransients = new ArrayList<>();

    public void setTransients(ArrayList<Transient> t){
        this.aTransients.addAll(t);
    }

    public ArrayList<Transient> getData(){
        return this.aTransients;
    }

    public ArrayList<Transient> removeTransient(){
        if(this.aTransients.size() > 1) {
            this.aTransients.remove(0);
        }
        return this.aTransients;
    }
}


