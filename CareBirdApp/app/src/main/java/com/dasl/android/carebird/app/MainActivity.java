package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Font path
        String fontPath = "fonts/APHont-Regular_q15c.ttf";

        // text view label
        TextView polyText1 = (TextView) findViewById(R.id.PolyWelcome);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_qr_code) {
            Intent i = new Intent(this, QRCodeActivity.class);
            startActivity(i);
            return true;
        }
        else if(id == R.id.action_contacts) {
            Intent i = new Intent(this, ContactActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
