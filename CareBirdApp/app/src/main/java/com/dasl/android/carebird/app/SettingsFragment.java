package com.dasl.android.carebird.app;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.app.AlarmManager;
import android.content.Intent;


import java.util.Calendar;
import java.util.Random;

/**
 * Created by crazz_000 on 5/4/2014.
 */
public class SettingsFragment extends PreferenceFragment {

    /*
     * For testing purposes
     */
    private AlarmManager keeperOfAlarms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.mainpreferences);

        // everything below here is just for testing use of Schedules
        // can be removed later
        ReminderSchedule a,b;
        a = new ReminderSchedule(21, 2, "penicillin");
        b = new ReminderSchedule(20, 4, "glucose");

        keeperOfAlarms = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
        exampleSchedulerMethod(a);
        exampleSchedulerMethod(b);


    }

    public void exampleSchedulerMethod(ReminderSchedule exampleSchedule) {
        int pseudoRand = (int) (System.currentTimeMillis() / 1000 + System.currentTimeMillis()
                        % 1000);

        Intent toReminder = new Intent(this.getActivity(), ReminderActivity.class);
        toReminder.putExtra("REMINDER_NAME", exampleSchedule.getName());
        toReminder.putExtra("YES_NO", exampleSchedule.getMessage());

        // Calendar to get simple time of day
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, exampleSchedule.getHour());
        cal.set(Calendar.MINUTE, exampleSchedule.getMinute());

        PendingIntent temp = PendingIntent.getActivity(this.getActivity(), pseudoRand,
                toReminder, PendingIntent.FLAG_UPDATE_CURRENT);
        keeperOfAlarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, temp);

        return;

    }
}
