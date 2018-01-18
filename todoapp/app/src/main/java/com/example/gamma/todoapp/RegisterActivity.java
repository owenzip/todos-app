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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.SignUpEvent;

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
    @BindView(R.id.layTitleRegister) LinearLayout mLayTitleRegister;
    @BindView(R.id.layBodyRegister) LinearLayout mLayBodyRegister;
    @BindView(R.id.btnAccept) Button mBtnAccept;
    @BindView(R.id.btnRegisterBack) TextView mBtnRegisterBack;
    ApiService mApiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // Start animation
        mLayTitleRegister.startAnimation(AnimationEffect.animTopToBottom(getApplicationContext()));
        mBtnAccept.startAnimation(AnimationEffect.animHideToZoom(getApplicationContext()));
        mBtnRegisterBack.startAnimation(AnimationEffect.animHideToZoom(getApplicationContext()));
        mLayBodyRegister.startAnimation(AnimationEffect.animLeftToRight(getApplicationContext()));
    }

    //Event click Register
    @OnClick(R.id.btnAccept)
    public void onClickBtnAccept(View view) {

        //Check Edittext
        if (mEdtRegisterUsername.length() < 3) {
            mTxvNofiticationRegister.setText(R.string.user_notstrong);
            setAnimNofitication();
        } else if (mEdtRegisterPassword.length() < 5) {
            mTxvNofiticationRegister.setText(R.string.pass_notstrong);
            setAnimNofitication();
        } else if (mEdtFirstname.length() < 2 || mEdtLastname.length() < 2) {
            mTxvNofiticationRegister.setText(R.string.firstlast_required);
            setAnimNofitication();
        } else {
            //Check Register
            if (mEdtRegisterPassword.getText().toString().equals(mEdtRegisterConfirmPassword.getText().toString())) {
                // Get animation
                getAnimAcceptClick();
                mBtnAccept.startAnimation(AnimationEffect.animHideToZoom(getApplicationContext()));
                mBtnAccept.startAnimation(AnimationEffect.animCircular(getApplicationContext()));

                String username = mEdtRegisterUsername.getText().toString();
                String password = mEdtRegisterPassword.getText().toString();
                String firstname = mEdtFirstname.getText().toString();
                String lastname = mEdtLastname.getText().toString();
                mApiservice = ApiUtils.getApiInterface();
                checkRegister(username, password, firstname, lastname);
            } else {
                mTxvNofiticationRegister.setText(R.string.pass_notmatch);
                setAnimNofitication();
            }
        }
    }

    @OnClick(R.id.btnRegisterBack)
    public void onClickBackRegister(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Constant.INTENT_TOKEN, LoginActivity.mAccessToken);
        intent.putExtra(Constant.INTENT_USERID, LoginActivity.mUserId);
        startActivity(intent);
        finish();
    }

    public void checkRegister(String username, String password, String firstname, String lastname) {
        mApiservice = ApiUtils.getApiInterface();
        mApiservice.register(username, password, firstname, lastname).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    // Follow user register with Fabric
                    Answers.getInstance().logSignUp(new SignUpEvent().putMethod(mEdtRegisterUsername.getText().toString()).putSuccess(true));
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra(Constant.INTENT_USER_REGISTER, mEdtRegisterUsername.getText().toString());
                    intent.putExtra(Constant.INTENT_PASS_REGISTER, mEdtRegisterPassword.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    mTxvNofiticationRegister.setText(getString(R.string.error_register));
                    clearAnimLoginClick();
                    // Follow user register with Fabric
                    Answers.getInstance().logSignUp(new SignUpEvent().putMethod(mEdtRegisterUsername.getText().toString()).putSuccess(false));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mTxvNofiticationRegister.setText(getString(R.string.error_system));
                clearAnimLoginClick();
                // Follow user register with Fabric
                Answers.getInstance().logSignUp(new SignUpEvent().putMethod(mEdtRegisterUsername.getText().toString()).putSuccess(false));
            }
        });
    }

    public void getAnimAcceptClick () {
        mTxvNofiticationRegister.setText("");
        mBtnAccept.setBackgroundResource(R.drawable.ic_loading);
        mBtnAccept.setText("");
        mBtnAccept.getLayoutParams().height = 100;
        mBtnAccept.getLayoutParams().width = 100;
    }

    public void clearAnimLoginClick () {
        mBtnAccept.setText("Accept");
        mBtnAccept.getLayoutParams().height = 130;
        mBtnAccept.getLayoutParams().width = 850;
        mBtnAccept.setBackgroundResource(R.drawable.bg_button);
        mBtnAccept.clearAnimation();
    }

    public void setAnimNofitication() {
        mTxvNofiticationRegister.startAnimation(AnimationEffect.animLeftToRight(getApplicationContext()));
    }
}
