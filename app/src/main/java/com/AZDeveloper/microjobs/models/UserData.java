package com.AZDeveloper.microjobs.models;

import androidx.lifecycle.LiveData;

//Class for UserData data to upload data on firestore
public class UserData extends LiveData<UserData> {
    String userId;
    private int balance, taskFinished, taskPending;

    public UserData(String userId, int balance, int taskFinished, int taskPending) {
        this.userId = userId;
        this.balance = balance;
        this.taskFinished = taskFinished;
        this.taskPending = taskPending;
    }

    public UserData() {
        this.userId = "";
        this.balance = 0;
        this.taskFinished = 0;
        this.taskPending = 0;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTaskFinished() {
        return taskFinished;
    }

    public void setTaskFinished(int taskFinished) {
        this.taskFinished = taskFinished;
    }

    public int getTaskPending() {
        return taskPending;
    }

    public void setTaskPending(int taskPending) {
        this.taskPending = taskPending;
    }
}
