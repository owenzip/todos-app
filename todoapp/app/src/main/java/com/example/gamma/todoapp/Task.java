/**
 * Task.java
 * Create by Nhut Nguyen
 * Date 30/11/2017
 */
package com.example.gamma.todoapp;

/* Model Task */
public class Task {

    private String mTask;
    private String mStatus;
    private String mTaskId;
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
