/**
 * ApiService.java
 * Create by Nhut Nguyen
 * Date 09/01/2018
 */
package com.example.gamma.todoapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*Controller API*/
public interface ApiService {

    @POST(Constant.URL_REGISTER)
    @FormUrlEncoded
    Call<User> register(@Field("username") String username, @Field("password") String password,
                        @Field("firstname") String firstname, @Field("lastname") String lastname);

    @POST(Constant.URL_LOGIN)
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType);

    @POST(Constant.URL_LOGIN)
    Call<User> userLogin(@Body CheckLogin checkLogin);

    @GET("/get")
    Call<ResponseBody> testGetUser(@Header("Authorization") String authToken);

}
