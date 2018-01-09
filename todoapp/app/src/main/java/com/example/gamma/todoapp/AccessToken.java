package com.example.gamma.todoapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gamma on 09/01/18.
 */

public class AccessToken {
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public AccessToken(String access_token) {
        this.accessToken = access_token;
    }

    public String getAccess_token() {
        return accessToken;
    }

    public void setAccess_token(String access_token) {
        this.accessToken = access_token;
    }
}
