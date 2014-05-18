package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/17/2014.
 */
public class Status{
    private int responseCode;
    private String message;

    public Status(int responseCode, String message){
        this.responseCode = responseCode;
        this.message = message;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public String getMessage(){
        return this.message;
    }
}
