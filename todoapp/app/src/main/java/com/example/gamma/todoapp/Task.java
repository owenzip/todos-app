/**
 * Task.java
 * Create by Nhut Nguyen
 * Date 30/11/2017
 */
package com.example.gamma.todoapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* Model Task table*/
public class Task {

    @SerializedName("taskId")
    @Expose
    private Integer taskId;

    @SerializedName("task")
    @Expose
    private String task;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
