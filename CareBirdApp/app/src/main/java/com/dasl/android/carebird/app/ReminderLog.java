package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public abstract class ReminderLog {
    public long logtime;
    public long originalalerttime;

    /** no arg constructor for json serialization */
    ReminderLog(){
    }

    public String getType(){return "unknown";}
    ReminderLog(long originalalerttime){
        logtime = System.currentTimeMillis();
        this.originalalerttime = originalalerttime;
    }
    /*public String toString(){
        return "logtime: "+logtime+", originalalerttime: "+originalalerttime;
    }*/

    //public abstract String toString();
}
