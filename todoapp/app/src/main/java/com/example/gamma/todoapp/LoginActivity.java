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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for CheckLogin */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txvAnim) TextView mTxvAnimNofi;
    @BindView(R.id.edtUsername) EditText mEdtUsername;
    @BindView(R.id.edtPassword) EditText mEdtPassword;
    @BindView(R.id.layAnimLogin) ViewGroup mLayAnimLogin;
    public static String mPasswordEcode;
    public static String mAccessToken;
    public static int mUserId;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mApiService = ApiUtils.getApiInterface();

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
            animTextNofi();
        } else if (mEdtPassword.length() <= 3) {
            mTxvAnimNofi.setText(R.string.pass_required);
            animTextNofi();
        } else {
            String username = mEdtUsername.getText().toString();
            String password = mEdtPassword.getText().toString();
            mApiService = ApiUtils.getApiInterface();
            checkLogin(username, password, Constant.GRANT_TYPE_VALUE);
        }
    }

    //Animation nofitication Text
    public void animTextNofi() {
        TransitionManager.beginDelayedTransition(mLayAnimLogin);
        mTxvAnimNofi.setVisibility(View.VISIBLE);
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
                } else {
                    mTxvAnimNofi.setText(R.string.error_login);
                    animTextNofi();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, getString(R.string.error_system) + t, Toast.LENGTH_SHORT).show();
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
}
