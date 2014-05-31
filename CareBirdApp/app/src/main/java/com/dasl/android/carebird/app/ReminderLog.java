package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class ReminderLog {
    public String getType() {
        return type;
    }

    private transient String type;
    public long logtime;
    public long originalalerttime;

    /** no arg constructor for json serialization */
    ReminderLog(){
    }

    ReminderLog(long originalalerttime, String type){
        logtime = System.currentTimeMillis();
        this.originalalerttime = originalalerttime;
        this.type = type;
    }
}
