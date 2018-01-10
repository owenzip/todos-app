/**
 * ApiService.java
 * Create by Nhut Nguyen
 * Date 09/01/2018
 */
package com.example.gamma.todoapp;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*API Controller*/
public interface ApiService {

    // User Register
    @POST(Constant.URL_REGISTER)
    @FormUrlEncoded
    Call<User> register(@Field(Constant.USER_NAME) String username, @Field(Constant.USER_PASS) String password,
                        @Field(Constant.USER_FIRSTNAME) String firstname, @Field(Constant.USER_LASTNAME) String lastname);

    // User Login with Auth
    @POST(Constant.URL_LOGIN)
    @FormUrlEncoded
    Call<AccessToken> login(@Header(Constant.AUTH_KEY) String authBasic, @Field(Constant.USER_NAME) String username, @Field(Constant.USER_PASS) String password, @Field(Constant.GRANT_TYPE_KEY) String grantType);

    // Get User Id when Login Success
    @GET(Constant.URL_GET_USERID)
    Call<User> getUserId(@Header(Constant.AUTH_KEY) String authToken, @Query(Constant.USER_NAME) String username);

    // Get all Task by UserId
    @GET(Constant.URL_GET_AND_DELETE)
    Call<Task> getTaskByUserId(@Header(Constant.AUTH_KEY) String authToken, @Path("userId") int userId);
}
