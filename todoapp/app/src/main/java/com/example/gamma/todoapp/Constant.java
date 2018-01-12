/**
 * Constant.java
 * Create by Nhut Nguyen
 * Date 04/01/2017
 */
package com.example.gamma.todoapp;

/* Class constant variables in application */
public class Constant {
    // API url
    public static final String URL_SERVER = "http://192.168.1.209:8080";
    public static final String URL_GET_USERID = URL_SERVER + "/users";
    public static final String URL_LOGIN = URL_SERVER + "/oauth/token";
    public static final String URL_REGISTER = URL_SERVER + "/users/register";
    public static final String URL_GET_AND_DELETE = URL_SERVER + "/users/{userId}/tasks";
    public static final String URL_GET_TASK_COMPLETED = URL_SERVER + "/users/{userId}/tasks/true";
    public static final String URL_GET_TASK_ACTIVE = URL_SERVER + "/users/{userId}/tasks/false";
    public static final String URL_ADD_TASK = URL_SERVER + "/tasks";
    public static final String URL_UPDATE_TASK = URL_SERVER + "/tasks/{taskid}";
    public static final String URL_UPDATE_STATUS = URL_SERVER + "/tasks/{taskId}/{status}";

    // Auth
    public static final String AUTH_KEY = "Authorization";
    public static final String AUTH_VALUE = "Bearer ";
    public static final String BASIC_USERNAME = "clientid";
    public static final String BASIC_PASSWORD = "clientsecret";
    public static final String BASIC_VALUE = "Basic ";
    public static final String GRANT_TYPE_KEY = "grant_type";
    public static final String GRANT_TYPE_VALUE = "password";

    // Response Json User
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";
    public static final String USER_PASS = "password";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_LASTNAME = "lastname";

    // Reponse Json Task
    public static final String TASK_ID = "taskId";
    public static final String TASK_TASK = "task";
    public static final String TASK_STATUS = "status";
    public static final String TASK_USERID = "userid";

    // Another
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String STATUS_TRUE = "true";
    public static final String STATUS_FALSE = "false";

    // Intent Name
    public static final String INTENT_TOKEN = "TOKEN";
    public static final String INTENT_USERID = "USERID";
    public static final String INTENT_USER_REGISTER = "USERREGISTER";
    public static final String INTENT_PASS_REGISTER = "PASSREGISTER";
}
