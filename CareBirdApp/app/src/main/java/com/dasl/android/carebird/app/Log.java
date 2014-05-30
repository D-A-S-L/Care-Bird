package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class Log {
    public long logTime;

    // not used when a log is created to add data to the database
    // but it is used when a log is created to store a result pulled from the database
    public transient int id;
    //public transient int count;

    Log(){
        // no arg constructor for json serialization
        logTime = System.currentTimeMillis();
    }
    /*
    Log(long logTime, int id){
        this.logTime = logTime;
        this.id = id;
    }
    */
}
