package com.AZDeveloper.microjobs.models;

import androidx.lifecycle.LiveData;

//Class for User data to upload data on firestore
public class User extends LiveData<User> {
    String userID,userName, userEmail;
    int balancePoints;

    public User(String userID, String userName, String userEmail) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
    }
    public User() {
        this.userID = null;
        this.userName = null;
        this.userEmail = null;
        this.balancePoints = 0;
    }

    public int getBalancePoints() {
        return balancePoints;
    }

    public void setBalancePoints(int balancePoints) {
        this.balancePoints = balancePoints;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
