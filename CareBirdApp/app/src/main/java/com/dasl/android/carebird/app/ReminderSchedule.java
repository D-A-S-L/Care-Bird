package com.dasl.android.carebird.app;

import android.app.AlarmManager;

/**
 * Created by Alec on 5/9/2014.
 */
public class ReminderSchedule {

        public transient final static long ONCE = 0;

        public transient final static long DAILY = AlarmManager.INTERVAL_DAY;

        public transient final static long TWICE_DAILY = AlarmManager.INTERVAL_HALF_DAY;

        public transient final static long WEEKLY = AlarmManager.INTERVAL_DAY * 7;

        private int minute;

        private int hour;

        private long interval;

        /*
         * Name would be name of medication, "glucose", etc.
         */
        private String name;


    /*
     * Constructor takes the hour, then the minute, and finally a String describing what
     * the Reminder is to be used for.
     *
     */
    public ReminderSchedule(int h, int m, String n, long i) {
        minute = m;
        hour = h;
        name = n;
        interval = i;
    }

    //public int getKey() {
        //return ((name + minute) + hour);
    //}

    public String toString() {
        String toRet = name + ": set at " + hour + ":" + minute + " at interval " + interval;

        return toRet;
    }

    public String getMessage() {
        if (name.equals("glucose"))
            return "Have you measured your glucose level?";

        return "Have you taken your " + name + " yet?";
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    public String getName() {
        return name;
    }
}
