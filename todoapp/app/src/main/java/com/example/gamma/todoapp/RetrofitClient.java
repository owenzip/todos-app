/**
 * RetrofitClient.java
 * Create by Nhut Nguyen
 * Date 09/01/2018
 */
package com.example.gamma.todoapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*Class configure Retrofit*/
public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
