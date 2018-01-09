/**
 * ApiUtils.java
 * Create by Nhut Nguyen
 * Date 09/01/2018
 */
package com.example.gamma.todoapp;

public class ApiUtils {
    private ApiUtils() {}
    public static ApiService getApiInterface (){
        return RetrofitClient.getClient(Constant.URL_SERVER).create(ApiService.class);
    }
}
