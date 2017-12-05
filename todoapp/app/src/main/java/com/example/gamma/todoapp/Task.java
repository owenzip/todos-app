/*
 * Task.java
 * Create by Nhut Nguyen
 * Date 30/11/2017
 */
package com.example.gamma.todoapp;

/*Class for get and set table Task*/
public class Task {
    private String task;
    private String status;
    private String taskId;
    private String userId;

    public String getTask() {
        return task;
    }

    public void setTask(String task) { this.task = task; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
