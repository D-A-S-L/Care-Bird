package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class PedometerLog extends ReminderLog {
    public byte interval; // 0 for day, 1 for week, 2 month, 3 year
    public long stepsTaken;

    PedometerLog(){
        super();
        // no arg constructor for json serialization
    }

    PedometerLog(int logTime, byte interval, long stepsTaken){
        super();
        this.interval = interval;
        this.stepsTaken = stepsTaken;
    }
}
