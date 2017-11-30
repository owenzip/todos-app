package com.example.gamma.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RegisterActivity extends AppCompatActivity {

    String url = "http://192.168.1.209:8080/users";
    EditText edtRegisterUsername,edtRegisterPassword,edtFirstname,edtLastname,edtRegisterConfirmPassword;
    TextView btnAccept,txvNofiticationRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegisterUsername = (EditText) findViewById(R.id.edtRegisterUsername);
        edtRegisterPassword = (EditText) findViewById(R.id.edtRegisterPassword);
        edtRegisterConfirmPassword = (EditText) findViewById(R.id.edtRegisterConfirmPassword);
        txvNofiticationRegister = (TextView) findViewById(R.id.txvNotificationRegister);
        edtFirstname = (EditText) findViewById(R.id.edtFirstname);
        edtLastname = (EditText) findViewById(R.id.edtLastname);
        btnAccept = (TextView) findViewById(R.id.btnAccept);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtRegisterUsername.length()<3)
                    txvNofiticationRegister.setText("Username must be greater than 3");
                else if (edtRegisterPassword.length() < 5)
                    txvNofiticationRegister.setText("Password's not strong enough");
                else if (edtFirstname.length() < 2 || edtLastname.length() < 2)
                    txvNofiticationRegister.setText("Firstname or Lastname is required");
                else {
                    if (edtRegisterPassword.getText().toString().equals(edtRegisterConfirmPassword.getText().toString())) {
                        checkRegister();
                    }
                    else
                        txvNofiticationRegister.setText("Password do not match");
                }
            }
        });
    }

    public void checkRegister(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("true")) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    txvNofiticationRegister.setText("Some error occured , check your network connection");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                txvNofiticationRegister.setText("Some error occured , check your network connection");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", edtRegisterUsername.getText().toString());
                parameters.put("password", edtRegisterPassword.getText().toString());
                parameters.put("firstname", edtFirstname.getText().toString());
                parameters.put("lastname", edtLastname.getText().toString());
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
        rQueue.add(request);
    }
}
