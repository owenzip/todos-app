/**
 * AccessToken.java
 * Create by Nhut Nguyen
 * Date 10/01/2018
 */
package com.example.gamma.todoapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*  Class POJO request oauth/token */
public class AccessToken {

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("expires_in")
    @Expose

    private String expiresIn;

    @SerializedName("scope")
    @Expose
    private String scope;

    @SerializedName("jti")
    @Expose
    private String jti;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) { this.jti = jti; }
}
