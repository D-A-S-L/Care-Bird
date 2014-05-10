package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by David on 5/6/14.
 */
public class MainCGActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cg_activity);

        final Button button1 = (Button) findViewById(R.id.Schedules);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ErrorActivity.class);
                startActivityForResult (myIntent, 0);
            }
        });

        final Button button2 = (Button) findViewById(R.id.CareReceivers);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), CareReceiversActivity.class);
                startActivityForResult (myIntent, 0);
            }
        });

        final Button button3 = (Button) findViewById(R.id.QRcode);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), QRCodeActivity.class);
                startActivityForResult (myIntent, 0);
            }
        });
    }
}
