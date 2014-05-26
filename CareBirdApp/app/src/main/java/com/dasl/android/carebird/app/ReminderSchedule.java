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

    public ReminderSchedule (String formatted) {
        int fieldInd = 0;

        for (int i = 0; i < formatted.length(); i++) {
            if (formatted.regionMatches(i, ": set at ", 0, 9)) {
                fieldInd = i + 9;
                name = formatted.substring(0, i);
                break;
            }
        }

        for (int i = fieldInd; i < formatted.length(); i++) {
            if (formatted.charAt(i) == ':') {
                hour = Integer.parseInt(formatted.substring(fieldInd, i));
                fieldInd = i + 1;
                break;
            }
        }

        for (int i = fieldInd; i < formatted.length(); i++) {
            if (formatted.regionMatches(i, " at interval ", 0, 13)) {
                minute = Integer.parseInt(formatted.substring(fieldInd, i));
                fieldInd = i + 13;
                break;
            }
        }

        interval = Long.parseLong(formatted.substring(fieldInd, formatted.length()));
    }

    public int getKey() {
        return ((name + minute) + hour).hashCode();
    }

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

    public Long getInterval() {
        return interval;
    }

    public boolean equals(Object o) {
        ReminderSchedule rs;

        try {
            rs = (ReminderSchedule) o;
        } catch (ClassCastException e) {
            return false;
        }

        if (name.equals(rs.getName()) && hour == rs.getHour() && minute == rs.getMinute()
                && interval == rs.getInterval())
            return true;

        return false;
    }
}
