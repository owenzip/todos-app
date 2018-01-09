/**
 * Constant.java
 * Create by Nhut Nguyen
 * Date 04/01/2017
 */
package com.example.gamma.todoapp;

/* Class constant variables in application */
public class Constant {
    // API url
    public static final String URL_SERVER = "http://clientid:clientsecret@192.168.1.209:8080";
    public static final String URL_GET_USERID = URL_SERVER + "/users?username=%1$s";
    public static final String URL_LOGIN = URL_SERVER + "/oauth/token";
    public static final String URL_REGISTER = URL_SERVER + "/users/register";
    public static final String URL_GET_AND_DELETE = URL_SERVER + "/users/%1$s/tasks";
    public static final String URL_GET_TASK_COMPLETED = URL_SERVER + "/users/%1$s/tasks/true";
    public static final String URL_GET_TASK_ACTIVE = URL_SERVER + "/users/%1$s/tasks/false";
    public static final String URL_ADD_TASK = URL_SERVER + "/tasks";
    public static final String URL_UPDATE_TASK = URL_SERVER + "/tasks/%1$s";
    public static final String GRANT_TYPE = "password";
}
