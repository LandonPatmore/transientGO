package com.google.android.stardroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.stardroid.R;
import com.google.android.stardroid.data.TransientData;

public class DetailTransientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transient);

        TextView r = (TextView) findViewById(R.id.RA);
        float ra = TransientData.INSTANCE.getData().get(this.getIntent().getExtras().getInt("location")).getR();
        String raString = Float.toString(ra);
        r.setText("RA: " + raString);

        TextView d = (TextView) findViewById(R.id.DEC);
        d.setText("DEC: ?????");

        TextView m = (TextView) findViewById(R.id.MAG);
        float mag = TransientData.INSTANCE.getData().get(this.getIntent().getExtras().getInt("location")).getM();
        String magString = Float.toString(mag);
        m.setText("MAG: " + magString);

        TextView t = (TextView) findViewById(R.id.datePublished);
        t.setText("Date Published: " + TransientData.INSTANCE.getData().get(this.getIntent().getExtras().getInt("location")).getDp());

        TextView tc = (TextView) findViewById(R.id.dateAlerted);
        tc.setText("Date Alerted: " + TransientData.INSTANCE.getData().get(this.getIntent().getExtras().getInt("location")).getDa());

        TextView p = (TextView) findViewById(R.id.type);
        p.setText("Type: " + TransientData.INSTANCE.getData().get(this.getIntent().getExtras().getInt("location")).getT());

        TextView id = (TextView) findViewById(R.id.link);
        id.setText("Link: " + TransientData.INSTANCE.getData().get(this.getIntent().getExtras().getInt("location")).getLink());

    }
}
