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

public class RegisterActivity extends AppCompatActivity {

    String url = "http://192.168.1.209:8080/users";
    EditText edtRegisterUsername;
    EditText edtRegisterPassword;
    EditText edtFirstname;
    EditText edtLastname;
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegisterUsername = (EditText) findViewById(R.id.edtRegisterUsername);
        edtRegisterPassword = (EditText) findViewById(R.id.edtRegisterPassword);
        edtFirstname = (EditText) findViewById(R.id.edtFirstname);
        edtLastname = (EditText) findViewById(R.id.edtLastname);
        btnAccept = (Button) findViewById(R.id.btnAccept);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("true")){
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this,TaskActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Can't Register", Toast.LENGTH_LONG).show();
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(RegisterActivity.this, "Some error occurred : "+volleyError, Toast.LENGTH_LONG).show();;
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
        });
    }
}
