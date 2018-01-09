/**
 * Task.java
 * Create by Nhut Nguyen
 * Date 30/11/2017
 */
package com.example.gamma.todoapp;
import com.google.gson.annotations.SerializedName;

/* Model Task table*/
public class Task {

    @SerializedName("task")
    private String mTask;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("taskId")
    private String mTaskId;

    @SerializedName("userId")
    private String mUserId;

    public Task(String mTask, String mStatus, String mTaskId, String mUserId) {
        this.mTask = mTask;
        this.mStatus = mStatus;
        this.mTaskId = mTaskId;
        this.mUserId = mUserId;
    }

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
