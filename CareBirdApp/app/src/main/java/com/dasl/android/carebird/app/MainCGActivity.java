package com.dasl.android.carebird.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * Created by David on 5/6/14.
 */
public class MainCGActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_cg_activity);

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

        final Button button1 = (Button) findViewById(R.id.Schedules);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ReminderListActivityCG.class);
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
        Button settingsBtn = (Button) findViewById(R.id.settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
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
                Intent i = new Intent(this, LocationActivity.class);
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
