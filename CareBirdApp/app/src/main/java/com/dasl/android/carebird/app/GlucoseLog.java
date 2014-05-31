package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/30/2014.
 */
public class GlucoseLog extends ReminderLog {
    private final transient String type = "glucose";
    public String message;
    public String actionTaken;

    /** no arg constructor for json serialization */
    GlucoseLog(){
        super();
    }

    GlucoseLog(long originalAlertTime, String type, String message, String actionTaken){
        super(originalAlertTime, type);
        this.message = message;
        this.actionTaken = actionTaken;
    }
}
