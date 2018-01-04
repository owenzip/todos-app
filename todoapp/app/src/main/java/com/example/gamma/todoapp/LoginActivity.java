/*
 * LoginActivity.java
 * Create by Nhut Nguyen
 * Date 29/11/2017
 */

package com.example.gamma.todoapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/* Class using for CheckLogin */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginActivity extends AppCompatActivity {

    public static String userId;

    @BindView(R.id.btnLogin)
    TextView btnLogin;
    @BindView(R.id.btnRegister)
    TextView btnRegister;
    @BindView(R.id.txvAnim)
    TextView txvAnimNofi;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.layAnimLogin)
    ViewGroup layAnimLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegister)
    public void onClickBtnRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @OnClick(R.id.btnLogin)
    public void onClickBtnLogin(View view) {


        if (edtUsername.length() <= 3) {
            txvAnimNofi.setText("Username's required");
            animTextNofi();
        } else if (edtPassword.length() <= 3) {
            txvAnimNofi.setText("Password's required");
            animTextNofi();
        } else {
            getUserId();
            checkLogin();
        }
    }

    //Animation nofitication Text
    public void animTextNofi() {

        boolean visible = false;
        TransitionManager.beginDelayedTransition(layAnimLogin);
        txvAnimNofi.setVisibility(View.VISIBLE);
    }

    //Get UserId by Username when User login successful
    public void getUserId() {

        String username = edtUsername.getText().toString();
        String urlUserId = String.format(Constant.URL_GET_USERID, username);
        StringRequest request = new StringRequest(Request.Method.GET, urlUserId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                userId = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txvAnimNofi.setText("Username's not found");
                animTextNofi();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
            }
        };
        TaskController.getPermission().addToRequestQueue(request);
    }

    //Check Login by Username and Password
    public void checkLogin() {

        StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                //If response Json return = true => Login successful
                if (s.equals("true")) {
                    startActivity(new Intent(LoginActivity.this, TaskActivity.class));
                } else {
                    txvAnimNofi.setText("Login failed , please try again");
                    animTextNofi();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                txvAnimNofi.setText("Some error occured , check your network connection");
                animTextNofi();
            }
        }) {
            //Get Username and Password from Edittext
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", edtUsername.getText().toString());
                parameters.put("password", edtPassword.getText().toString());
                return parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
            }
        };
        TaskController.getPermission().addToRequestQueue(request);
    }
}
