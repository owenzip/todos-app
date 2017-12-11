/*
 * RegisterActivity.java
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
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
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

/*Class using for User Register*/
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RegisterActivity extends AppCompatActivity {

    String url = "http://192.168.1.207:8080/users";
    EditText edtRegisterUsername,edtRegisterPassword,edtFirstname,edtLastname,edtRegisterConfirmPassword;
    TextView btnAccept,txvNofiticationRegister;
    ViewGroup layAnimRegister;

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
        layAnimRegister = (ViewGroup) findViewById(R.id.layAnimRegister);

        //Event click Accept button
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check Edittext
                if (edtRegisterUsername.length()<3){
                    txvNofiticationRegister.setText("Username must be greater than 3");
                    animTextNofi();
                } else if (edtRegisterPassword.length() < 5){
                    txvNofiticationRegister.setText("Password's not strong enough");
                    animTextNofi();
                } else if (edtFirstname.length() < 2 || edtLastname.length() < 2){
                    txvNofiticationRegister.setText("Firstname or Lastname is required");
                    animTextNofi();
                }
                //Check Register
                else {
                    if (edtRegisterPassword.getText().toString().equals(edtRegisterConfirmPassword.getText().toString())) {
                        checkRegister();
                    } else{
                        txvNofiticationRegister.setText("Password do not match");
                        animTextNofi();
                    }
                }
            }
        });
    }

    //Animation nofitication Text
    public void animTextNofi(){

        boolean visible = false;
        TransitionManager.beginDelayedTransition(layAnimRegister);
        visible = !visible;
        txvNofiticationRegister.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    //Check Register
    public void checkRegister(){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //If response return = true else Register successful
                if (s.equals("true")) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    txvNofiticationRegister.setText("Some error occured , check your network connection");
                    animTextNofi();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                txvNofiticationRegister.setText("Some error occured , check your network connection");
                animTextNofi();
            }
        }) {
            //Get Username, Password, Firstname, Lastname from Register
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", edtRegisterUsername.getText().toString());
                parameters.put("password", edtRegisterPassword.getText().toString());
                parameters.put("firstname", edtFirstname.getText().toString());
                parameters.put("lastname", edtLastname.getText().toString());
                return parameters;
            }
            //Check authentication REST API
            HashMap<String, String> createBasicAuthHeader(String username, String password) {
                HashMap<String, String> headerMap = new HashMap<String, String>();

                String credentials = username + ":" + password;
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headerMap.put("Authorization", "Basic " + base64EncodedCredentials);

                return headerMap;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return createBasicAuthHeader("admin", "admin");
            }
        };
        TaskController.getPermission().addToRequestQueue(request);
    }
}
