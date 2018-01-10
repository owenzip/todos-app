package com.example.gamma.todoapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gamma on 10/01/18.
 */

public class CheckLogin {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("grant_type")
    @Expose
    private String grantType;

    public CheckLogin(String username, String password, String grantType) {
        this.username = username;
        this.password = password;
        this.grantType = grantType;
    }
}
