package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/30/2014.
 */
public class GlucoseLog extends ReminderLog {
    public String message;
    public String actionTaken; // 0 for snooze, 1 for yes, 2 for dismiss(user exits alert without answering)

    /** no arg constructor for json serialization */
    GlucoseLog(){
        super();
    }

    GlucoseLog(long originalAlertTime, String message, String actionTaken){
        super(originalAlertTime);
        this.message = message;
        this.actionTaken = actionTaken;
    }
}
