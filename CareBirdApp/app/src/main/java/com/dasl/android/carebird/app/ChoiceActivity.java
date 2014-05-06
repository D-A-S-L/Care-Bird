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
public class ChoiceActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_activity);

        final Button button1 = (Button) findViewById(R.id.CareGiverButton);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), MainMenuActivity.class);
                startActivityForResult (myIntent, 0);
            }
        });

        final Button button2 = (Button) findViewById(R.id.CareReceiverButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), LoginCRActivity.class);
                startActivityForResult (myIntent, 0);
            }
        });
    }
}
