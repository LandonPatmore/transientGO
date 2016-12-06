package com.google.android.stardroid.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.stardroid.data.Transient;
import com.google.android.stardroid.data.TransientData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
            String jsonStr = sh.makeServiceCall(url);

            Log.d(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    Log.d(TAG, "NOT NULL DEBUG");
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray transients = jsonObj.getJSONArray("result");

                    // looping through All Transient
                    for (int i = 0; i < transients.length(); i++) {
                        JSONObject c = transients.getJSONObject(i);

                        String author = c.getString("author");
                        String transientId = c.getString("transientId");
                        String dateAlerted = c.getString("dateAlerted");
                        String datePublished = c.getString("datePublished");
                        float right_asencsion = (float) c.getDouble("right_asencsion");
                        float declination = (float) c.getDouble("declination");

                        t = new Transient(author, transientId, dateAlerted, datePublished, "TRANSIENT", right_asencsion, declination, 20.1f, 10);

                        // adding trans to trans list
                        transientList.add(t);
                    }
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
