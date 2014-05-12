package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by David on 4/30/14.
 */
public class ChoiceActivity extends Activity {
  //  SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
    protected void onCreate(Bundle savedInstanceState) {


        int firstboot = getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getInt("firstboot", 0);

        if (firstboot == 0){
            // 1) Launch the authentication activity
            super.onCreate(savedInstanceState);
            setContentView(R.layout.choice_activity);

            final Button button1 = (Button) findViewById(R.id.CareGiverButton);
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit().putBoolean(getString(R.string.user_type), true).commit();

                    getSharedPreferences("BOOT_PREF", MODE_PRIVATE)
                            .edit()
                            .putInt("firstboot", 1)
                            .commit();

                    Intent myIntent = new Intent(v.getContext(), LoginCGActivity.class);
                    startActivityForResult (myIntent, 0);
                }
            });

            final Button button2 = (Button) findViewById(R.id.CareReceiverButton);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    getSharedPreferences("BOOT_PREF", MODE_PRIVATE).edit().putBoolean(getString(R.string.user_type), false).commit();

                    getSharedPreferences("BOOT_PREF", MODE_PRIVATE)
                            .edit()
                            .putInt("firstboot", 1)
                            .commit();

                    Intent myIntent = new Intent(v.getContext(), LoginCRActivity.class);
                    startActivityForResult (myIntent, 0);
                }
            });
        } else if (firstboot == 1) {
            super.onCreate(savedInstanceState);
            if(getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getBoolean(getString(R.string.user_type), true)) {
                Intent myIntent = new Intent(this, LoginCGActivity.class);
                startActivityForResult(myIntent, 0);
            } else {
                Intent myIntent = new Intent(this, LoginCRActivity.class);
                startActivityForResult(myIntent, 0);
            }
        } else {
            super.onCreate(savedInstanceState);
            if(getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getBoolean(getString(R.string.user_type), true)) {
                Intent myIntent = new Intent(this, MainCGActivity.class);
                startActivityForResult(myIntent, 0);
            } else {
                Intent myIntent = new Intent(this, MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        }

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
