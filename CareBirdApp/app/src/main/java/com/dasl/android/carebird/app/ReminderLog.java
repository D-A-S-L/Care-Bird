package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class ReminderLog {
    public long logtime;
    public long originalalerttime;

    /** no arg constructor for json serialization */
    ReminderLog(){
    }

    ReminderLog(long originalalerttime){
        logtime = System.currentTimeMillis();
        this.originalalerttime = originalalerttime;
    }
    public static String getType() {
        return "unknown";
    }
}
