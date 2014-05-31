package com.dasl.android.carebird.app;

import android.app.Activity;

/**
 * Created by Brian on 5/20/2014.
 */
public class User {
    private String uname;
    private String pass;
    private String fname;
    private String lname;
    private String phonenum;
    private String token = "";

    public User(){
        //no argument constructor, required for the Gson serializer
    }
    public User(String userName, String password, String firstName, String lastName, String PhoneNum){
        this.uname = userName;
        this.pass = password;
        this.fname = firstName;
        this.lname = lastName;
        this.phonenum = PhoneNum;
    }

    public void setToken(String token){
        this.token = token;
    }
    public void setPassword(String pass) {
        this.pass = pass;
    }
    public String getUserName(){
        return this.uname;
    }
    public String getPassword(){
        return this.pass;
    }
    public String getFirstName(){
        return this.fname;
    }
    public String getLastName(){
        return this.lname;
    }
    public String getToken(){
        return this.token;
    }
    public String getPhoneNum(){
        return this.phonenum;
    }
    public String toString(){
        return fname + " " + lname;
    }
    /*
    public boolean equals(User otherUser){
        return otherUser.getUserName().equals(((GlobalApplication) Activity.getApplication()).getMe());
    }
    */
}