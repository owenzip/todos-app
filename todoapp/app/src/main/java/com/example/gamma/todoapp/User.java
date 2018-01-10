/**
 * User.java
 * Create by Nhut Nguyen
 * Date 09/01/2018
 */
package com.example.gamma.todoapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*Model User table*/
public class User {

    @SerializedName(Constant.USER_ID)
    @Expose
    private int mUserId;

    @SerializedName(Constant.USER_NAME)
    @Expose
    private String mUsername;

    @SerializedName(Constant.USER_PASS)
    @Expose
    private String mPassword;

    @SerializedName(Constant.USER_FIRSTNAME)
    @Expose
    private String mFirstname;

    @SerializedName(Constant.USER_LASTNAME)
    @Expose
    private String mLastname;

    public User(int mUserId, String mUsername, String mPassword, String mFirstname, String mLastname) {
        this.mUserId = mUserId;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mFirstname = mFirstname;
        this.mLastname = mLastname;
    }


    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmFirstname() {
        return mFirstname;
    }

    public void setmFirstname(String mFirstname) {
        this.mFirstname = mFirstname;
    }

    public String getmLastname() {
        return mLastname;
    }

    public void setmLastname(String mLastname) {
        this.mLastname = mLastname;
    }
}
