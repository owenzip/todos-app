/**
 * Authentication.java
 * Create by Nhut Nguyen
 * Date 04/01/2017
 */
package com.example.gamma.todoapp;

import android.util.Base64;

import java.util.HashMap;

/* Class using for check Auth */
public class Authentication {

    public static HashMap<String, String> createBasicAuthHeader(String username, String password) {

        HashMap<String, String> headerMap = new HashMap<String, String>();
        String credentials = username + ":" + password;
        String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + base64EncodedCredentials);
        return headerMap;
    }
}
