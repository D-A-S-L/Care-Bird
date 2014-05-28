package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by David on 4/30/14.
 */
public class MainMenuActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        class PostTask extends AsyncTask<String, Integer, String> {
            @Override
            protected String doInBackground(String... params) {
                User me = new User(params[0],params[1],params[2],params[3], params[4]);
                com.dasl.android.carebird.app.Status response;
                String result;
                try {
                    response = ((GlobalApplication) getApplication()).getDatabase().login(me);
                    result = response.getMessage();
                }catch (IOException error){
                    result = "failure in try catch";
                };
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                Context context = getApplicationContext();
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                Log.v("carebird", result);
            }
        }
        new PostTask().execute(new String[]{getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("userName", "userName"), getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("password", "password"), getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("fname", "fname"), getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("lname", "lname"), getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("myPhoneNumber", "1111111111")});

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

        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(this.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        final Button button0 = (Button) findViewById(R.id.Panic);
        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_CALL, Uri.parse(getSharedPreferences("BOOT_PREF", MODE_PRIVATE).getString("otherPhoneNumber", "tel:1111111")));
                startActivityForResult (myIntent, 0);
            }
        });

        final Button button1 = (Button) findViewById(R.id.Schedules);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ReminderListActivity.class);
                startActivityForResult (myIntent, 0);
            }
        });

        final Button button2 = (Button) findViewById(R.id.CareGivers);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), CareGiversActivity.class);
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

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_contacts:
                Intent i1 = new Intent(this, ContactActivity.class);
                startActivity(i1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
