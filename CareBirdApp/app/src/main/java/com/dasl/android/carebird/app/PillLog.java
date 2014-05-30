package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/24/2014.
 */
public class PillLog extends ReminderLog {
    public String message;
    public String actiontaken; // 0 for snooze, 1 for yes, 2 for dismiss(user exits alert without answering)

    /** no arg constructor for json serialization */
    PillLog(){
        super();
    }

    PillLog(long originalalerttime, String message, String actiontaken){
        super(originalalerttime);
        this.message = message;
        this.actiontaken = actiontaken;
    }
}
