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

    @BindView(R.id.txvAnim) TextView mTxvAnimNofi;
    @BindView(R.id.edtUsername) EditText mEdtUsername;
    @BindView(R.id.edtPassword) EditText mEdtPassword;
    @BindView(R.id.layAnimLogin) ViewGroup mLayAnimLogin;

    public static String sUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegister)
    public void onClickBtnRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnLogin)
    public void onClickBtnLogin(View view) {
        if (mEdtUsername.length() <= 3) {
            mTxvAnimNofi.setText(R.string.user_required);
            animTextNofi();
        } else if (mEdtPassword.length() <= 3) {
            mTxvAnimNofi.setText(R.string.pass_required);
            animTextNofi();
        } else {
            getUserId();
            checkLogin();
        }
    }
    //Animation nofitication Text
    public void animTextNofi() {
        TransitionManager.beginDelayedTransition(mLayAnimLogin);
        mTxvAnimNofi.setVisibility(View.VISIBLE);
    }
    //Get UserId by Username when User login successful
    public void getUserId() {
        String username = mEdtUsername.getText().toString();
        StringRequest request = new StringRequest(Request.Method.GET, String.format(Constant.URL_GET_USERID, username), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sUserId = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxvAnimNofi.setText(R.string.user_notfound);
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
                    mTxvAnimNofi.setText(R.string.error_connection);
                    animTextNofi();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxvAnimNofi.setText(getString(R.string.error_system) + error);
                animTextNofi();
            }
        }) {
            //Get Username and Password from Edittext
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", mEdtUsername.getText().toString());
                parameters.put("password", mEdtPassword.getText().toString());
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
