package com.dasl.android.carebird.app;

/**
 * Created by Alec on 5/9/2014.
 */
public class ReminderSchedule {

    private int minute;

    private int hour;

    /*
     * Name would be name of medication, "glucose", etc.
     */
    private String name;

    private boolean active;


    /*
     * Constructor takes the hour, then the minute, and finally a String describing what
     * the Reminder is to be used for.
     *
     */
    public ReminderSchedule(int h, int m, String n) {
        minute = m;
        hour = h;
        name = n;
        active = false;
    }

    public String getKey() {
        return ((name + minute) + hour);
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

    public void setActive(boolean a) {
        active = a;
    }

    public boolean isActive() {
        return active;
    }
}
