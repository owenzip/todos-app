package com.example.gamma.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    TextView btnLogin,btnRegister,txvNotificationLogin;
    EditText edtUsername,edtPassword,edtAdd;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txvNotificationLogin = (TextView) findViewById(R.id.txvNotificationLogin);
        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtAdd = (EditText) findViewById(R.id.edtAdd);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.length() <= 3)
                    txvNotificationLogin.setText("Username's required");
                else if (edtPassword.length() <= 3)
                    txvNotificationLogin.setText("Password's required");
                else {
                    getUserId();
                    checkLogin();

                }
            }
        });
    }

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
                txvNotificationLogin.setText("Username's not found");
            }
        });
        TaskController.getPermission().addToRequestQueue(request);
    }

    public void checkLogin(){
        String urlLogin = "http://192.168.1.209:8080/users/login";
        StringRequest request = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                if(s.equals("true")){
                    startActivity(new Intent(LoginActivity.this,TaskActivity.class));
                }
                else{
                    txvNotificationLogin.setText("Login failed , please try again");
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                txvNotificationLogin.setText("Some error occured , check your network connection");
            }
        }) {
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
