package com.dasl.android.carebird.app;

import android.app.Application;

/**
 * Created by David on 5/21/2014.
 */
public class GlobalApplication extends Application{

    private static Database db = new Database();
    //private static User me;

    public Database getDatabase() {
        return db;
    }

    public void setMe(User me) {
       db.me = me;
    }

    public User getMe() {
        return db.me;
    }
}
