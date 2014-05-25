package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class glucoseTable extends Log {
    public int originalAlertTime;
    public int glucoseValue; // if alert is snoozed or dismissed then use -1 for this value

    glucoseTable(){
        super();
        // no arg constructor for json serialization
    }

    glucoseTable(int logTime, int originalAlertTime, int glucoseValue){
        super(logTime, -1, -1);
        this.originalAlertTime = originalAlertTime;
        this.glucoseValue = glucoseValue;
    }
}
