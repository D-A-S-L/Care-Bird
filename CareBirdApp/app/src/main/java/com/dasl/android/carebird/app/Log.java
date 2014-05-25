package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class Log {
    public int logTime;

    // not used when a log is created to add data to the database
    // but it is used when a log is created to store a result pulled from the database
    public transient int id;
    public transient int count;

    Log(){
        // no arg constructor for json serialization
    }

    Log(int logTime, int id, int count){
        this.logTime = logTime;
        this.id = id;
        this.count = count;
    }
}
