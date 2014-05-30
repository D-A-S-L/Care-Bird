package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class LocationLog extends Log {
    public long originalAlertTime;
    public double distanceFromHome;
    public double latitude;
    public double longitude;

    LocationLog(){
        super();
        // no arg constructor for json serialization
    }

    LocationLog(long originalAlertTime, double distanceFromHome, double latitude, double longitude){
        super();
        this.originalAlertTime = originalAlertTime;
        this.distanceFromHome = distanceFromHome;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
