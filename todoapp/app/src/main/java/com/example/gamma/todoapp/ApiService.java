/**
 * ApiService.java
 * Create by Nhut Nguyen
 * Date 09/01/2018
 */
package com.example.gamma.todoapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*API Controller*/
public interface ApiService {

    // User Register
    @POST(Constant.URL_REGISTER)
    @FormUrlEncoded
    Call<User> register(@Field(Constant.USER_NAME) String username, @Field(Constant.USER_PASS) String password, @Field(Constant.USER_FIRSTNAME) String firstname, @Field(Constant.USER_LASTNAME) String lastname);

    // User Login with Auth
    @POST(Constant.URL_LOGIN)
    @FormUrlEncoded
    Call<AccessToken> login(@Header(Constant.AUTH_KEY) String authBasic, @Field(Constant.USER_NAME) String username, @Field(Constant.USER_PASS) String password, @Field(Constant.GRANT_TYPE_KEY) String grantType);

    // Get User Id when Login Success
    @GET(Constant.URL_GET_USERID)
    Call<User> getUserId(@Header(Constant.AUTH_KEY) String authToken, @Query(Constant.USER_NAME) String username);

    // Get all Task by UserId
    @GET(Constant.URL_GET_AND_DELETE)
    Call<ResponseBody> getAllTask(@Header(Constant.AUTH_KEY) String authToken, @Path("userId") int userId);

    // Get al Task active
    @GET(Constant.URL_GET_TASK_ACTIVE)
    Call<ResponseBody> getTaskActive(@Header(Constant.AUTH_KEY) String authToken, @Path("userId") int userId);

    // Get all Task completed
    @GET(Constant.URL_GET_TASK_COMPLETED)
    Call<ResponseBody> getTaskCompleted(@Header(Constant.AUTH_KEY) String authToken, @Path("userId") int userId);

    // Delete Tast completed
    @DELETE(Constant.URL_GET_AND_DELETE)
    Call<ResponseBody> deleteTaskCompleted(@Header(Constant.AUTH_KEY) String authToken, @Path("userId") int userId);

    // Add new Task
    @POST(Constant.URL_ADD_TASK)
    @FormUrlEncoded
    Call<Task> addTask(@Header(Constant.AUTH_KEY) String authToken, @Field(Constant.TASK_USERID) int userId, @Field(Constant.TASK_TASK) String task);

    // Update Task
    @PUT(Constant.URL_UPDATE_TASK)
    @FormUrlEncoded
    Call<ResponseBody> updateTask(@Header(Constant.AUTH_KEY) String authToken, @Path(Constant.TASK_ID) String taskId,@Field(Constant.TASK_TASK) String task, @Field(Constant.TASK_STATUS) String status);

    // Update Status
    @PUT(Constant.URL_UPDATE_TASK)
    Call<Task> updateStatus(@Header(Constant.AUTH_KEY) String authToken, @Path(Constant.TASK_ID) String taskId, @Path(Constant.TASK_STATUS) String status);
}
