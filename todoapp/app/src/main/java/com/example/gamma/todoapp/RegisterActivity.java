/**
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

/* Class using for User Register */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edtRegisterUsername)
    EditText edtRegisterUsername;
    @BindView(R.id.edtRegisterPassword)
    EditText edtRegisterPassword;
    @BindView(R.id.edtRegisterConfirmPassword)
    EditText edtRegisterConfirmPassword;
    @BindView(R.id.edtFirstname)
    EditText edtFirstname;
    @BindView(R.id.edtLastname)
    EditText edtLastname;
    @BindView(R.id.btnAccept)
    TextView btnAccept;
    @BindView(R.id.txvNotificationRegister)
    TextView txvNofiticationRegister;
    @BindView(R.id.layAnimRegister)
    ViewGroup layAnimRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAccept)
    public void onClickBtnAccept(View view) {

        //Check Edittext
        if (edtRegisterUsername.length() < 3) {
            txvNofiticationRegister.setText(R.string.user_notstrong);
            txvNofiticationRegister.setTextColor(getResources().getColor(R.color.colorEdtText));
            animTextNofi();
        } else if (edtRegisterPassword.length() < 5) {
            txvNofiticationRegister.setText(R.string.pass_notstrong);
            animTextNofi();
        } else if (edtFirstname.length() < 2 || edtLastname.length() < 2) {
            txvNofiticationRegister.setText(R.string.firstlast_required);
            animTextNofi();
        }
        //Check Register
        else {
            if (edtRegisterPassword.getText().toString().equals(edtRegisterConfirmPassword.getText().toString())) {
                checkRegister();
            } else {
                txvNofiticationRegister.setText(R.string.pass_notmatch);
                animTextNofi();
            }
        }
    }

    //Animation nofitication Text
    public void animTextNofi() {
        boolean visible = false;
        TransitionManager.beginDelayedTransition(layAnimRegister);
        txvNofiticationRegister.setVisibility(View.VISIBLE);
    }

    //Check Register
    public void checkRegister() {

        StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //If response return = true else Register successful
                if (s.equals("Successful")) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    txvNofiticationRegister.setText(R.string.error_connection);
                    animTextNofi();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txvNofiticationRegister.setText(R.string.error_system + error.toString());
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
        };
        TaskController.getPermission().addToRequestQueue(request);
    }
}
