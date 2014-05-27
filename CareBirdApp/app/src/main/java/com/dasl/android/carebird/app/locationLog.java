package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class locationLog extends Log {
    public int originalAlertTime;
    public int distanceFromHome;
    public long latitude;
    public long longitude;

    locationLog(){
        super();
        // no arg constructor for json serialization
    }

    locationLog(int logTime, int originalAlertTime, int distanceFromHome, long latitude, long longitude){
        super(logTime, -1, -1);
        this.originalAlertTime = originalAlertTime;
        this.distanceFromHome = distanceFromHome;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
