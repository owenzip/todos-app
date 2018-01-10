/**
 * LoginActivity.java
 * Create by Nhut Nguyen
 * Date 29/11/2017
 */
package com.example.gamma.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/* Class using for CheckLogin */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txvAnim) TextView mTxvAnimNofi;
    @BindView(R.id.edtUsername) EditText mEdtUsername;
    @BindView(R.id.edtPassword) EditText mEdtPassword;
    @BindView(R.id.layAnimLogin) ViewGroup mLayAnimLogin;
    ApiService mApiService;
    int mUserId;
    String mAccessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnRegister)
    public void onClickBtnRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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

    //Check login
    public void checkLogin(final String username, String password, String grantType) {
        mApiService.login(RetrofitClient.getAuthBasic(), username, password, grantType).enqueue(new Callback<AccessToken>() {

            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    String username = mEdtUsername.getText().toString();
                    mAccessToken = response.body().getAccessToken();
                    getUserId(Constant.AUTH_VALUE + mAccessToken, username);
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

    public void getUserId(String accessToken, String username) {
        Call<User> call = mApiService.getUserId(accessToken, username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    mUserId = response.body().getmUserId();
                    Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                    intent.putExtra(Constant.INTENT_TOKEN, mAccessToken);
                    intent.putExtra(Constant.INTENT_USERID, mUserId);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this,R.string.error_login,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Error : " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
