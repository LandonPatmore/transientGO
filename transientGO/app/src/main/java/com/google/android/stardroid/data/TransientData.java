package com.google.android.stardroid.data;

import java.util.ArrayList;

/**
 * Created by Zackary on 12/1/2016.
 */
public enum TransientData {
    INSTANCE;

    private ArrayList<Transient> aTransients = new ArrayList<>();
    private ArrayList<Transient> cTransients = new ArrayList<>();

    public void setTransients(ArrayList<Transient> t){
        this.aTransients.addAll(t);
        this.aTransients.add(new Transient("No more transients!", 0f, 0f, 0f, "", "", "", 0));
    }

    public void setCaught(Transient t){
        this.cTransients.add(t);
    }

    public ArrayList<Transient> getData(){
        return this.aTransients;
    }

    public ArrayList<Transient> getCaught(){return this.cTransients;}

    public ArrayList<Transient> removeTransient(){
        if(this.aTransients.size() > 1) {
            this.aTransients.remove(0);
        }

        return this.aTransients;
    }
}


