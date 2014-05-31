package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class LocationLog extends ReminderLog {
    private final transient String type = "location";
    public double metersfromhome;
    public double latitude;
    public double longitude;

    /** no arg constructor for json serialization */
    LocationLog(){
        super();
    }

    LocationLog(long originalalerttime, String type, double metersfromhome, double latitude, double longitude){
        super(originalalerttime, type);
        this.metersfromhome = metersfromhome;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
