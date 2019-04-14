package com.AZDeveloper.microjobs.models;

public class UserInfo {
    private String userId, userName, userMail, phoneNo;

    public UserInfo(String userId, String userName, String userMail, String phoneNo) {
        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
        this.phoneNo = phoneNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
