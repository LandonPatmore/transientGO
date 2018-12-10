package com.google.android.stardroid.data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

/**
 * Created by landon on 11/30/16.
 */

public class JSONParser {

    // URL to get contacts JSON
    private static String url = "http://pi.cs.oswego.edu/~lpatmore/getAllTransients.php";

    ArrayList<Transient> transientList;

    private Transient t;

    public JSONParser(){
        transientList = new ArrayList<>();
    }

    public void execute(){
        new GetTransients().execute();
    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetTransients extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.sendGetRequest(url);

            Log.d(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    Log.d(TAG, "NOT NULL DEBUG");
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray transients = jsonObj.getJSONArray("result");
                    System.out.println("ARRAY LENGTH " + transients.length());

                    // looping through All Transient
                    for (int i = 0; i < transients.length(); i++) {
                        JSONObject c = transients.getJSONObject(i);

                        String transientid = c.getString("transientid");
                        float RA = (float)c.getDouble("RA");
                        float declination = (float)c.getDouble("declination");
                        float MAG = (float) c.getDouble("MAG");
                        String pictureURL = c.getString("pictureURL");
                        String lightCurveURL = c.getString("lightCurveURL");
                        String dateFound = c.getString("dateFound");
                        int score = c.getInt("score");

                        t = new Transient(transientid, RA, declination, MAG, pictureURL, lightCurveURL, dateFound, score);

                        // adding trans to trans list
                        transientList.add(t);
                    }
                    Collections.shuffle(transientList);
                    TransientData.INSTANCE.setTransients(transientList);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }
            return null;
        }

    }
}
