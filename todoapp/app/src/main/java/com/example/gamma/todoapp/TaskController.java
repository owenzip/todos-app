/*
 * Taskcontroller.java
 * Create by Nhut Nguyen
 * Date 28/11/2017
 */

package com.example.gamma.todoapp;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/* Class using for control Response */
public class TaskController extends Application {

    public static final String TAG = TaskController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static TaskController mTaskController;

    @Override
    public void onCreate() {
        super.onCreate();
        mTaskController = this;
    }

    public static synchronized TaskController getPermission() {
        return mTaskController;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
