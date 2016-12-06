package com.google.android.stardroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.stardroid.R;
import com.google.android.stardroid.data.TransientData;
import com.google.android.stardroid.data.UserData;

/**
 * Created by landon on 12/3/16.
 */

public class CaughtTransientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caught_transient);

        TextView r = (TextView) findViewById(R.id.RA);
        float ra = TransientData.INSTANCE.getData().get(0).getR();
        String raString = Float.toString(ra);
        r.setText(raString);

        TextView d = (TextView) findViewById(R.id.DEC);
        float dec = TransientData.INSTANCE.getData().get(0).getD();
        String decString = Float.toString(dec);
        d.setText(decString);

        TextView m = (TextView) findViewById(R.id.MAG);
        float mag = TransientData.INSTANCE.getData().get(0).getM();
        String magString = Float.toString(mag);
        m.setText(magString);

        TextView t = (TextView) findViewById(R.id.datePublished);
        t.setText(TransientData.INSTANCE.getData().get(0).getDp());

        TextView tc = (TextView) findViewById(R.id.dateAlerted);
        tc.setText(TransientData.INSTANCE.getData().get(0).getDa());

        TextView p = (TextView) findViewById(R.id.type);
        p.setText(TransientData.INSTANCE.getData().get(0).getT());

        TextView id = (TextView) findViewById(R.id.link);
        id.setText(TransientData.INSTANCE.getData().get(0).getLink());

        Button b = (Button) findViewById(R.id.doneButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransientData.INSTANCE.removeTransient();
                startActivity(new Intent(CaughtTransientActivity.this, DynamicStarMapActivity.class));
                Toast.makeText(CaughtTransientActivity.this, "Refreshed Data", Toast.LENGTH_SHORT).show();
            }
        });

        int addedScore = UserData.INSTANCE.getUserExp() + TransientData.INSTANCE.getData().get(0).getScore();
        UserData.INSTANCE.setUserExp(addedScore);


    }

}
