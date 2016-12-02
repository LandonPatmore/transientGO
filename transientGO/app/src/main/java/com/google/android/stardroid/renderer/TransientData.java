package com.google.android.stardroid.renderer;

import com.google.android.stardroid.units.GeocentricCoordinates;

/**
 * Created by Zackary on 12/1/2016.
 */
public class TransientData {
    private static TransientData ourInstance = new TransientData();

    public static TransientData getInstance() {
        return ourInstance;
    }

    private float ra;
    private float dec;

    private TransientData() {
    }

    public GeocentricCoordinates getCo() {
        ra = (float) 55.00;
        dec = (float) 66.00;

        return GeocentricCoordinates.getInstance(ra, dec);
    }
}


