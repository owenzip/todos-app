/**
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for CheckLogin */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txvAnim) TextView mTxvAnimNofi;
    @BindView(R.id.edtUsername) EditText mEdtUsername;
    @BindView(R.id.edtPassword) EditText mEdtPassword;
    @BindView(R.id.btnLogin) Button mBtnLogin;
    @BindView(R.id.layTitleLogin) LinearLayout mLayTitleLogin;
    @BindView(R.id.layUsernamePassword) LinearLayout mLayUserPass;
    public static String mPasswordEcode;
    public static String mAccessToken;
    public static int mUserId;
    ApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics()); // Add Fabric
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mApiService = ApiUtils.getApiInterface();

        // Get username and password from RegisterActivity if user register
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.INTENT_PASS_REGISTER) && intent.hasExtra(Constant.INTENT_USER_REGISTER)) {
            String usernameRegister = intent.getStringExtra(Constant.INTENT_USER_REGISTER);
            String passwordRegister = intent.getStringExtra(Constant.INTENT_PASS_REGISTER);
            mEdtUsername.setText(usernameRegister);
            mEdtPassword.setText(passwordRegister);
        } else {
            mEdtUsername.setText("");
            mEdtPassword.setText("");
        }

        // Start animation
        mLayTitleLogin.startAnimation(AnimationEffect.animLeftToRight(getApplicationContext()));
        mBtnLogin.startAnimation(AnimationEffect.animHideToZoom(getApplicationContext()));
        mLayUserPass.startAnimation(AnimationEffect.animHideToZoom(getApplicationContext()));

    }

    @OnClick(R.id.btnRegister)
    public void onClickBtnRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnLogin)
    public void onClickBtnLogin(View view) {
        if (mEdtUsername.length() <= 3) {
            mTxvAnimNofi.setText(R.string.user_required);
            setAnimNofitication();
        } else if (mEdtPassword.length() <= 3) {
            mTxvAnimNofi.setText(R.string.pass_required);
            setAnimNofitication();
        } else {
            mApiService = ApiUtils.getApiInterface();
            checkLogin(mEdtUsername.getText().toString(), mEdtPassword.getText().toString(), Constant.GRANT_TYPE_VALUE);
            getAnimLogin();
            mBtnLogin.startAnimation(AnimationEffect.animHideToZoom(getApplicationContext()));
            mBtnLogin.startAnimation(AnimationEffect.animCircular(getApplicationContext()));
        }
    }

    //Check login and Get AccessToken
    public void checkLogin(final String username, String password, String grantType) {
        mApiService.login(RetrofitClient.getAuthBasic(), username, password, grantType).enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    String username = mEdtUsername.getText().toString();
                    mAccessToken = response.body().getAccessToken();
                    getUserId(Constant.AUTH_VALUE + mAccessToken, username);
                    mPasswordEcode = mEdtPassword.getText().toString();
                    // Follow user login success with Fabric
                    Answers.getInstance().logLogin(new LoginEvent().putMethod(username).putSuccess(true));
                } else {
                    // Follow user login false with Fabric
                    Answers.getInstance().logLogin(new LoginEvent().putMethod(username).putSuccess(false));
                    mTxvAnimNofi.setText(R.string.error_login);
                    clearAnimLogin();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                // Follow user login falses with Fabric
                Answers.getInstance().logLogin(new LoginEvent().putMethod(username).putSuccess(false));
                mTxvAnimNofi.setText(R.string.error_system);
                clearAnimLogin();
            }
        });
    }

    // Get UserId when user login success
    public void getUserId(String accessToken, String username) {
        mApiService.getUserId(accessToken, username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mUserId = response.body().getmUserId();
                    Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                    intent.putExtra(Constant.INTENT_TOKEN, mAccessToken);
                    intent.putExtra(Constant.INTENT_USERID, mUserId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error : " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAnimLogin () {
        mTxvAnimNofi.setText("");
        mBtnLogin.setBackgroundResource(R.drawable.ic_loading);
        mBtnLogin.setText("");
        mBtnLogin.getLayoutParams().height = 100;
        mBtnLogin.getLayoutParams().width = 100;
    }

    public void clearAnimLogin () {
        mBtnLogin.setText("Login");
        mBtnLogin.getLayoutParams().height = 130;
        mBtnLogin.getLayoutParams().width = 850;
        mBtnLogin.setBackgroundResource(R.drawable.bg_button);
        mBtnLogin.clearAnimation();
    }

    public void setAnimNofitication() {
        mTxvAnimNofi.startAnimation(AnimationEffect.animLeftToRight(getApplicationContext()));
    }
}
