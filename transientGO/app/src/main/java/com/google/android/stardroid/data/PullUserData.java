package com.google.android.stardroid.data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by landon on 12/5/16.
 */

public class PullUserData {

    // URL to get contacts JSON
    private static String url = "http://pi.cs.oswego.edu/~lpatmore/getUser.php";

    private UserData u = UserData.INSTANCE;

    public PullUserData(){
    }

    public void execute(){
        new GetUsers().execute();
    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetUsers extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String user = "bob";

            // Making a request to url and getting response
            String jsonStr = sh.sendGetRequestParam(url, user);

            Log.d(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    Log.d(TAG, "NOT NULL DEBUG");
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.d(TAG, "PAST OBJECT");

                    // Getting JSON Array node
                    JSONArray transients = jsonObj.getJSONArray("result");

                    JSONObject c = transients.getJSONObject(0);

                    String userName = c.getString("userName");
                    System.out.println("USERNAME: " + userName);
                    int userExp = c.getInt("userExp");
                    int userLevel = c.getInt("userLevel");
                    int userScore = c.getInt("userScore");

                    u.setUserName(userName);
                    u.setUserExp(userExp);
                    u.setUserLevel(userLevel);
                    u.setUserScore(userScore);

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
