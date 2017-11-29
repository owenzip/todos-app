package com.example.gamma.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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

    String urlLogin = "http://192.168.1.209:8080/users/login";
    Button btnLogin,btnRegister;
    EditText edtUsername,edtPassword,edtAdd;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
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
                checkLogin();
            }
        });
    }

    public void getUserId(){
        String username = edtUsername.getText().toString();
        String urlUserId = String.format("http://192.168.1.209:8080/users?username=%1$s",username);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlUserId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                userId = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(stringRequest);
    }

    public void checkLogin(){
        StringRequest request = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                if(s.equals("true")){
                    getUserId();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,TaskActivity.class));
                    getUserId();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Incorrect Details", Toast.LENGTH_LONG).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
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
        RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(request);
    }
}
