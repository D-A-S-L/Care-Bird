package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class LocationLog extends ReminderLog {
    public static final transient String type = "location";
    public double metersfromhome;
    public double latitude;
    public double longitude;

    /** no arg constructor for json serialization *///
    //
    LocationLog(){
        super();
    }

    LocationLog(long originalalerttime, double metersfromhome, double lat, double longitude){
        super(originalalerttime);
        this.metersfromhome = metersfromhome;
        this.latitude = lat;
        this.longitude = longitude;
    }
    @Override
    public String getType() {
        return LocationLog.type;
    }
    public String toString(){
        return super.toString()+", Type: "+type+ ", metersfromhome: "+metersfromhome
                +", latitude: "+latitude+", longitude: "+longitude;
    }
}
