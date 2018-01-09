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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/* Class using for CheckLogin */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txvAnim) TextView mTxvAnimNofi;
    @BindView(R.id.edtUsername) EditText mEdtUsername;
    @BindView(R.id.edtPassword) EditText mEdtPassword;
    @BindView(R.id.layAnimLogin) ViewGroup mLayAnimLogin;

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
    //Event click Login
    @OnClick(R.id.btnLogin)
    public void onClickBtnLogin(View view) {
        if (mEdtUsername.length() <= 3) {
            mTxvAnimNofi.setText(R.string.user_required);
            animTextNofi();
        } else if (mEdtPassword.length() <= 3) {
            mTxvAnimNofi.setText(R.string.pass_required);
            animTextNofi();
        } else {
            // ckech login here (coming soon)...
        }
    }
    //Animation nofitication Text
    public void animTextNofi() {
        TransitionManager.beginDelayedTransition(mLayAnimLogin);
        mTxvAnimNofi.setVisibility(View.VISIBLE);
    }
}
