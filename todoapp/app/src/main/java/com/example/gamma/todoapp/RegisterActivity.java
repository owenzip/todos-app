/**
 * RegisterActivity.java
 * Create by Nhut Nguyen
 * Date 29/11/2017
 */
package com.example.gamma.todoapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ApiService mApiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
    //Event click Register
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
                String username = mEdtRegisterUsername.getText().toString();
                String password = mEdtRegisterPassword.getText().toString();
                String firstname = mEdtFirstname.getText().toString();
                String lastname = mEdtLastname.getText().toString();
                mApiservice = ApiUtils.getApiInterface();

                checkRegister(username, password, firstname, lastname);
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
    //Check register
    public void checkRegister(String username, String password, String firstname, String lastname){
        mApiservice.register(username, password, firstname, lastname).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().toString() == "Successful") {
                    mTxvNofiticationRegister.setTextColor(getResources().getColor(R.color.colorAccept));
                    mTxvNofiticationRegister.setText(R.string.success_register);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                    mTxvNofiticationRegister.setText(R.string.error_register);
            }
        });
    }
}
