package com.dasl.android.carebird.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.os.Vibrator;

import java.util.Calendar;

/**
 * Created by David on 4/30/14.
 */
public class ReminderActivity extends Activity {

    private AlarmManager snoozeAlarm;

    private String remName;

    private String remMessage;

    private WakeLock wl;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //remName = getIntent().

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_activity);

        // statement by Amir
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        // Font path
        String fontPath = "fonts/APHont-Regular_q15c.ttf";

        // text view label
        TextView polyText1 = (TextView) findViewById(R.id.Reminder);
        polyText1.setText(getIntent().getExtras().getString("YES_NO"));
        Button yesButton = (Button) findViewById(R.id.YesOpt);
        Button noButton = (Button) findViewById(R.id.NoOpt);

        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        polyText1.setTypeface(tf);
        yesButton.setTypeface(tf);
        noButton.setTypeface(tf);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP),
                "Reminder");
        wl.acquire();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);

        yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wl.release();

                if (getIntent().getExtras().getString("REMINDER_NAME").equals("glucose")) {
                    glucInputScreen(savedInstanceState);
                } else {
                    // log successful medication entry

                    finish();
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wl.release();
                snooze(getIntent());

                nag(savedInstanceState);
            }
        });
    }

    private void glucInputScreen(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glucose_input);
    }

    private void nag(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_nag);

        TextView nText = (TextView) findViewById(R.id.nagText);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/APHont-Regular_q15c.ttf");
        nText.setTypeface(tf);

        Button ret = (Button) findViewById(R.id.Return);
        ret.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void snooze(Intent prevInstance) {

        snoozeAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int pseudoRand = (int) (System.currentTimeMillis() / 1000 +
                System.currentTimeMillis() % 1000);

        Intent toReminder = new Intent(this, ReminderActivity.class);
        toReminder.putExtra("REMINDER_NAME", prevInstance.getExtras().getString("REMINDER_NAME"));
        toReminder.putExtra("YES_NO", prevInstance.getExtras().getString("YES_NO"));

        PendingIntent temp = PendingIntent.getActivity(this, pseudoRand,
                toReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        snoozeAlarm.set(AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + (20 * 1000)),
                temp);

    }
}