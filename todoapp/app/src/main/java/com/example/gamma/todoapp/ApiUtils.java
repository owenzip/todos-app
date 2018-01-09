package com.example.gamma.todoapp;

/**
 * Created by gamma on 09/01/18.
 */

public class ApiUtils {
    private ApiUtils() {}
    public static ApiService getApiInterface (){
        return RetrofitClient.getClient(Constant.URL_SERVER).create(ApiService.class);
    }
}
