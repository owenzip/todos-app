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

    @BindView(R.id.edtRegisterUsername) EditText mEdtRegisterUsername;
    @BindView(R.id.edtRegisterPassword) EditText mEdtRegisterPassword;
    @BindView(R.id.edtRegisterConfirmPassword) EditText mEdtRegisterConfirmPassword;
    @BindView(R.id.edtFirstname) EditText mEdtFirstname;
    @BindView(R.id.edtLastname) EditText mEdtLastname;
    @BindView(R.id.txvNotificationRegister) TextView mTxvNofiticationRegister;
    @BindView(R.id.layAnimRegister) ViewGroup mLayAnimRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAccept)
    public void onClickBtnAccept(View view) {
        //Check Edittext
        if (mEdtRegisterUsername.length() < 3) {
            mTxvNofiticationRegister.setText(R.string.user_notstrong);
            animTextNofi();
        } else if (mEdtRegisterUsername.length() < 5) {
            mTxvNofiticationRegister.setText(R.string.pass_notstrong);
            animTextNofi();
        } else if (mEdtFirstname.length() < 2 || mEdtLastname.length() < 2) {
            mTxvNofiticationRegister.setText(R.string.firstlast_required);
            animTextNofi();
        }
        //Check Register
        else {
            if (mEdtRegisterPassword.getText().toString().equals(mEdtRegisterConfirmPassword.getText().toString())) {
                checkRegister();
            } else {
                mTxvNofiticationRegister.setText(R.string.pass_notmatch);
                animTextNofi();
            }
        }
    }
    //Animation nofitication Text
    public void animTextNofi() {
        TransitionManager.beginDelayedTransition(mLayAnimRegister);
        mTxvNofiticationRegister.setVisibility(View.VISIBLE);
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
                    mTxvNofiticationRegister.setText(R.string.error_connection);
                    animTextNofi();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxvNofiticationRegister.setText(getString(R.string.error_system) + error);
                animTextNofi();
            }
        }) {
            //Get Username, Password, Firstname, Lastname from Register
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", mEdtRegisterUsername.getText().toString());
                parameters.put("password", mEdtRegisterPassword.getText().toString());
                parameters.put("firstname", mEdtFirstname.getText().toString());
                parameters.put("lastname", mEdtLastname.getText().toString());
                return parameters;
            }
        };
        TaskController.getPermission().addToRequestQueue(request);
    }
}
