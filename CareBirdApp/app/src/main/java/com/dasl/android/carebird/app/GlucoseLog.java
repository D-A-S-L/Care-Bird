package com.dasl.android.carebird.app;

import java.util.Date;

/**
 * Created by Brian on 5/30/2014.
 */
public class GlucoseLog extends ReminderLog {
    public static final transient String type = "glucose";
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
    @Override
    public String getType() { return GlucoseLog.type;  }

    public String toString() {
        return "The response to glucose reminder was: " + actiontaken + " (original alarm" +
                " time was " + (new Date(originalalerttime)).toString();
    }
}
