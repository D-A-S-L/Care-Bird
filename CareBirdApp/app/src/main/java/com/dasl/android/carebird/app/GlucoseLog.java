package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/30/2014.
 */
public class GlucoseLog extends ReminderLog {
    private final transient String type = "glucose";
    public double glucosevalue;
    public String actiontaken;

    /** no arg constructor for json serialization */
    GlucoseLog(){
        super();
    }

    GlucoseLog(long originalAlertTime, String type, double glucosevalue, String actiontaken){
        super(originalAlertTime, type);
        this.glucosevalue = glucosevalue;
        this.actiontaken = actiontaken;
    }
}
