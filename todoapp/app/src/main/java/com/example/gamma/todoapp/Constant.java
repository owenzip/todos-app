/**
 * Constant.java
 * Create by Nhut Nguyen
 * Date 04/01/2017
 */
package com.example.gamma.todoapp;

/* Class constant variables in application */
public class Constant {

    // API url
    public static final String URL_GET_USERID = "http://192.168.1.207:8080/users?username=%1$s";
    public static final String URL_LOGIN = "http://192.168.1.207:8080/oauth/token";
    public static final String URL_REGISTER = "http://192.168.1.207:8080/users/register";
    public static final String URL_GET_AND_DELETE = "http://192.168.1.207:8080/users/%1$s/tasks";
    public static final String URL_GET_TASK_COMPLETED = "http://192.168.1.207:8080/users/%1$s/tasks/true";
    public static final String URL_GET_TASK_ACTIVE = "http://192.168.1.207:8080/users/%1$s/tasks/false";
    public static final String URL_ADD_TASK = "http://192.168.1.207:8080/tasks";
    public static final String URL_UPDATE_TASK = "http://192.168.1.207:8080/tasks/%1$s";

    // Constant Auth
    public static final String BASIC_AUTH_USERNAME = "clientid";
    public static final String BASIC_AUTH_PASSWORD = "clientsecret";
    public static final String BASIC_AUTH_KEY = "Authorization";
    public static final String BASIC_AUTH_VALUE = "Basic ";
}
