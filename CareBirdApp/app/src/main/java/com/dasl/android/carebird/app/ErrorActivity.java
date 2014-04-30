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
public class ErrorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_activity);

        // Font path
        String fontPath = "fonts/APHont-Regular_q15c.ttf";

        // text view label
        TextView polyText1 = (TextView) findViewById(R.id.Error);
        TextView mainMenuButton = (TextView) findViewById(R.id.MainMenu);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        polyText1.setTypeface(tf);
        mainMenuButton.setTypeface(tf);

        final Button button = (Button) findViewById(R.id.MainMenu);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MainMenuActivity.class);
                startActivityForResult (myIntent, 0);
            }
        });
    }
}
