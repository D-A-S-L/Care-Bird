package com.dasl.android.carebird.app;

import android.app.Application;

/**
 * Created by David on 5/21/2014.
 */
public class GlobalApplication extends Application{

    private Database db = new Database();

    public Database getDatabase() {
        return db;
    }
}
