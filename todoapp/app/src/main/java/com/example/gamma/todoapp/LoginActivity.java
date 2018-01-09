/*
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for CheckLogin */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txvAnim) TextView mTxvNofiticationLogin;
    @BindView(R.id.edtUsername) EditText mEdtUsername;
    @BindView(R.id.edtPassword) EditText mEdtPassword;
    @BindView(R.id.layAnimLogin) ViewGroup mLayAnimLogin;
    private ApiService mApiService;
    public static String sUserId;

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
        mApiService = ApiUtils.getApiInterface();
        String username = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        if (mEdtUsername.length() <= 3) {
            mTxvNofiticationLogin.setText(R.string.required_user);
            animTextNofi();
        } else if (mEdtPassword.length() <= 3) {
            mTxvNofiticationLogin.setText(R.string.required_password);
            animTextNofi();
        } else {
            login(username,password,Constant.GRANT_TYPE);
        }
    }

    //Animation nofitication Text
    public void animTextNofi() {
        TransitionManager.beginDelayedTransition(mLayAnimLogin);
        mTxvNofiticationLogin.setVisibility(View.VISIBLE);
    }

    // Check login
    public void login(String username, String password, String grantType){

        mApiService.login(username, password, grantType).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==200) {
                    mTxvNofiticationLogin.setTextColor(getResources().getColor(R.color.colorAccept));
                    mTxvNofiticationLogin.setText(R.string.success_register);
                    animTextNofi();
                } else {
                    mTxvNofiticationLogin.setText(R.string.error_register);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mTxvNofiticationLogin.setText(R.string.error_register);
            }
        });
    }
}
