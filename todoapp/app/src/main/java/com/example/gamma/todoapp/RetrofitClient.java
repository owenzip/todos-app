/**
 * RetrofitClient.java
 * Create by Nhut Nguyen
 * Date 09/01/2018
 */
package com.example.gamma.todoapp;

import android.util.Base64;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*Class configure Retrofit*/
public class RetrofitClient {

    private static Retrofit sRetrofit = null;

    /*Configure Retrofit and Okhttp*/
    public static Retrofit getClient(String baseUrl) {
        if (sRetrofit == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            Gson gson = gsonBuilder.create();
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
            client.readTimeout(30, TimeUnit.SECONDS);
            client.connectTimeout(30, TimeUnit.SECONDS);
            OkHttpClient okHttpClient = client.build();

            sRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .build();
        }
        return sRetrofit;
    }

    /*Create Basic Auth with Base64*/
    public static String getAuthBasic() {
        byte[] data = new byte[0];
        try {
            data = (Constant.BASIC_USERNAME + ":" + Constant.BASIC_PASSWORD).getBytes(Constant.CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Constant.BASIC_VALUE + Base64.encodeToString(data, Base64.NO_WRAP);
    }
}
