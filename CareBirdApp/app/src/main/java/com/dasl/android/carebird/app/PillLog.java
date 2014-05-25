package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class PillLog extends Log {
    public int originalAlertTime;
    public String message;
    public byte actionTaken; // 0 for snooze, 1 for yes, 2 for dismiss(user exits alert without answering)

    PillLog(){
        super();
        // no arg constructor for json serialization
    }

    PillLog(int logTime, int originalAlertTime, String message, byte actionTaken){
        super(logTime, -1, -1);
        this.originalAlertTime = originalAlertTime;
        this.message = message;
        this.actionTaken = actionTaken;
    }
}
