package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by David on 4/30/14.
 */
public class MainMenuActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        // Font path
        String fontPath = "fonts/APHont-Regular_q15c.ttf";

        // text view label
        TextView schedulesButton = (TextView) findViewById(R.id.Schedules);
        TextView careGiversButton = (TextView) findViewById(R.id.CareGivers);
        TextView settingsButton = (TextView) findViewById(R.id.QRcode);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        schedulesButton.setTypeface(tf);
        careGiversButton.setTypeface(tf);
        settingsButton.setTypeface(tf);

        final Button button1 = (Button) findViewById(R.id.Schedules);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ErrorActivity.class);
                startActivityForResult (myIntent, 0);
            }
        });

        final Button button2 = (Button) findViewById(R.id.CareGivers);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ReminderActivity.class);
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
