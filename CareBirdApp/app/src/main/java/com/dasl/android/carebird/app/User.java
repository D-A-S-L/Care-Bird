package com.dasl.android.carebird.app;

/**
 * Created by Brian on 5/20/2014.
 */
public class User {
    private String UName;
    private String pass;
    private String FName;
    private String LName;
    private transient  String token;

    public User(){
        //no argument constructor, required for the Gson serializer
    }
    public User(String userName, String password, String firstName, String lastName){
        this.UName = userName;
        this.pass = password;
        this.FName = firstName;
        this.LName = lastName;
    }

    public void setToken(String token){
        this.token = token;
    }
    public String getUserName(){
        return this.UName;
    }
    public String getPassword(){
        return this.pass;
    }
    public String getFirstName(){
        return this.FName;
    }
    public String getLastName(){
        return this.LName;
    }
    public String getToken(){
        return this.token;
    }
}