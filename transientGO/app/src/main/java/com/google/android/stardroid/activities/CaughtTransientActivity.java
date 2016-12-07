package com.google.android.stardroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.stardroid.R;
import com.google.android.stardroid.data.TransientData;
import com.google.android.stardroid.data.UserData;
import com.koushikdutta.ion.Ion;

/**
 * Created by landon on 12/3/16.
 */

public class CaughtTransientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caught_transient);

        TextView id = (TextView) findViewById(R.id.transientId);
        id.setText(TransientData.INSTANCE.getData().get(0).gettId());

        TextView r = (TextView) findViewById(R.id.RA);
        float ra = TransientData.INSTANCE.getData().get(0).getRa();
        String raString = Float.toString(ra);
        r.setText("RA: " + raString);

        TextView d = (TextView) findViewById(R.id.DEC);
        float dec = TransientData.INSTANCE.getData().get(0).getDecl();
        String decString = Float.toString(dec);
        d.setText("DEC: " + decString);

        TextView m = (TextView) findViewById(R.id.MAG);
        float mag = TransientData.INSTANCE.getData().get(0).getMag();
        String magString = Float.toString(mag);
        m.setText("MAG: " + magString);

        TextView t = (TextView) findViewById(R.id.dateFound);
        t.setText("Date Discovered: " + TransientData.INSTANCE.getData().get(0).getdF());

        TextView tc = (TextView) findViewById(R.id.score);
        int score = TransientData.INSTANCE.getData().get(0).getScore();
        String scoreString = Integer.toString(score);
        tc.setText("Points: " + scoreString);

        ImageView p = (ImageView) findViewById(R.id.picURL);
        Ion.with(p)
                .load(TransientData.INSTANCE.getData().get(0).getpURL());

        ImageView l = (ImageView) findViewById(R.id.lightURL);
        Ion.with(l)
                .load(TransientData.INSTANCE.getData().get(0).getlURL());


        Button b = (Button) findViewById(R.id.doneButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransientData.INSTANCE.setCaught(TransientData.INSTANCE.getData().get(0));
                TransientData.INSTANCE.removeTransient();
                startActivity(new Intent(CaughtTransientActivity.this, DynamicStarMapActivity.class));
            }
        });

        UserData.INSTANCE.setUserExp(UserData.INSTANCE.getUserExp() + TransientData.INSTANCE.getData().get(0).getScore());
        System.out.println("TOTAL EXP: " + UserData.INSTANCE.getTotalUserExp());
        if(UserData.INSTANCE.getUserExp() >= 100){
            int exp = UserData.INSTANCE.getUserExp();
            int buffer = exp - 100;
            exp = exp - buffer;

            int newexp = UserData.INSTANCE.getUserExp() - exp;
            UserData.INSTANCE.setUserExp(newexp);

            UserData.INSTANCE.setUserLevel(UserData.INSTANCE.getUserLevel() + 1);
            System.out.println("TOTAL EXP (INSIDE): " + UserData.INSTANCE.getTotalUserExp());
        }
        UserData.INSTANCE.setUserScore(UserData.INSTANCE.getUserScore() + TransientData.INSTANCE.getData().get(0).getScore());


    }

}
