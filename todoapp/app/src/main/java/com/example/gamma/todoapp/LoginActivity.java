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

/*Class using for CheckLogin*/
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginActivity extends AppCompatActivity {

    public static String userId;
    TextView btnLogin,btnRegister,txvAnimNofi;
    EditText edtUsername,edtPassword,edtAdd;
    ViewGroup layAnimLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtAdd = (EditText) findViewById(R.id.edtAdd);
        txvAnimNofi = (TextView) findViewById(R.id.txvAnim);
        layAnimLogin = (ViewGroup) findViewById(R.id.layAnimLogin);

        //Event click Register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Event click Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (edtUsername.length() <= 3){
                    txvAnimNofi.setText("Username's required");
                    animTextNofi();
                } else if (edtPassword.length() <= 3){
                    txvAnimNofi.setText("Password's required");
                    animTextNofi();
                } else {
                    getUserId();
                    checkLogin();
                }
            }
        });
    }

    //Animation nofitication Text
    public void animTextNofi(){

        boolean visible = false;
        TransitionManager.beginDelayedTransition(layAnimLogin);
        visible = !visible;
        txvAnimNofi.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    //Get UserId by Username when User login successful
    public void getUserId(){
        String username = edtUsername.getText().toString();
        String urlUserId = String.format("http://192.168.1.209:8080/users?username=%1$s",username);
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
        });
        TaskController.getPermission().addToRequestQueue(request);
    }

    //Check Login by Username and Password
    public void checkLogin(){

        String urlLogin = "http://192.168.1.209:8080/users/login";
        StringRequest request = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                //If response Json return = true => Login successful
                if(s.equals("true")){
                    startActivity(new Intent(LoginActivity.this,TaskActivity.class));
                }
                else{
                    txvAnimNofi.setText("Login failed , please try again");
                    animTextNofi();
                }
            }
        },new Response.ErrorListener(){

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
        };
        TaskController.getPermission().addToRequestQueue(request);
    }
}
