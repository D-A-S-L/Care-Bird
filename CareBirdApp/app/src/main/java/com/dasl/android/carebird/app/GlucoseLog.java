package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/30/2014.
 */
public class GlucoseLog extends ReminderLog {
    private static final transient String type = "glucose";
    public double glucosevalue;
    public String actiontaken;

    /** no arg constructor for json serialization */
    GlucoseLog(){
        super();
    }

    GlucoseLog(long originalAlertTime, double glucosevalue, String actiontaken){
        super(originalAlertTime);
        this.glucosevalue = glucosevalue;
        this.actiontaken = actiontaken;
    }
    public static String getType() {
        return type;
    }
}
