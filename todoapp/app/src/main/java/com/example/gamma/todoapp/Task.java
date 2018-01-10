/**
 * Task.java
 * Create by Nhut Nguyen
 * Date 30/11/2017
 */
package com.example.gamma.todoapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/* Model Task table*/
public class Task {

    @SerializedName(Constant.TASK_TASK)
    @Expose
    private String mTask;

    @SerializedName(Constant.TASK_STATUS)
    @Expose
    private String mStatus;

    @SerializedName(Constant.TASK_ID)
    @Expose
    private String mTaskId;

    @SerializedName(Constant.TASK_USERID)
    @Expose
    private String mUserId;

    public String getTask() {
        return mTask;
    }

    public void setTask(String task) {
        this.mTask = task;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public String getTaskId() {
        return mTaskId;
    }

    public void setTaskId(String taskId) {
        this.mTaskId = taskId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }
}
