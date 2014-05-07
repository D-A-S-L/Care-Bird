package com.dasl.android.carebird.app;

/**
 * Created by crazz_000 on 5/6/2014.
 */
public class Contact {
    private String userName;
    private String personName;
    private String phoneNum;
    private int userId;

    public Contact(String userName, String personName, String phoneNum, int userId) {
        this.userName = userName;
        this.personName = personName;
        this.phoneNum = phoneNum;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
