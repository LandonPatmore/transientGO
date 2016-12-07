package com.google.android.stardroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.stardroid.R;
import com.google.android.stardroid.data.TransientData;
import com.koushikdutta.ion.Ion;

public class DetailTransientActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transient);

        int position = this.getIntent().getExtras().getInt("location");

        TextView id = (TextView) findViewById(R.id.dtid);
        id.setText(TransientData.INSTANCE.getData().get(position).gettId());

        TextView r = (TextView) findViewById(R.id.dRA);
        float ra = TransientData.INSTANCE.getData().get(position).getRa();
        String raString = Float.toString(ra);
        r.setText("RA: " + raString);

        TextView d = (TextView) findViewById(R.id.dDEC);
        float dec = TransientData.INSTANCE.getData().get(position).getDecl();
        String decString = Float.toString(dec);
        d.setText("DEC: " + decString);

        TextView m = (TextView) findViewById(R.id.dMAG);
        float mag = TransientData.INSTANCE.getData().get(position).getMag();
        String magString = Float.toString(mag);
        m.setText("MAG: " + magString);

        TextView t = (TextView) findViewById(R.id.ddf);
        t.setText("Date Discovered: " + TransientData.INSTANCE.getData().get(position).getdF());

        TextView tc = (TextView) findViewById(R.id.ds);
        tc.setText("Points: " + TransientData.INSTANCE.getData().get(position).getScore());

        ImageView p = (ImageView) findViewById(R.id.dpu);
        Ion.with(p)
                .load(TransientData.INSTANCE.getData().get(position).getpURL());

        ImageView l = (ImageView) findViewById(R.id.dlc);
        Ion.with(l)
                .load(TransientData.INSTANCE.getData().get(position).getlURL());



    }
}
