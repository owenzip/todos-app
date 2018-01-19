/**
 * ChangePasswordActivity.java
 * Create by Nhut Nguyen
 * Date 15/01/2018
 */
package com.example.gamma.todoapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.InviteEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for User change password */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.edtChangeOldPassword) EditText mEdtChangeOldPassword;
    @BindView(R.id.edtChangeNewPassword) EditText mEdtChangeNewPassword;
    @BindView(R.id.edtChangeConfirmPassword) EditText mEdtChangeConfirmPassword;
    @BindView(R.id.txvChangeNofi) TextView mTxvChangeNofi;
    @BindView(R.id.layBodyChangePassword) LinearLayout mLayBodyChangePassword;
    @BindView(R.id.layTitleChangePassword) LinearLayout mLayTitleChangePassword;
    @BindView(R.id.layFooterChangePassword) LinearLayout mLayFooterChangePassword;
    @BindView(R.id.btnChangeAccept) Button mBtnChangeAccept;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        //Star animation
        mLayTitleChangePassword.startAnimation(AnimationEffect.animTopToBottom(getApplicationContext()));
        mLayBodyChangePassword.startAnimation(AnimationEffect.animLeftToRight(getApplicationContext()));
        mLayFooterChangePassword.startAnimation(AnimationEffect.animHideToZoom(getApplicationContext()));

    }

    // Event click button Change Password
    @OnClick(R.id.btnChangeAccept)
    public void changePassword(View view) {
        // Check textView
        if (mEdtChangeOldPassword.length() < 1) {
            mTxvChangeNofi.setText(R.string.old_pass_required);
            setAnimNofitication();
        } else if (mEdtChangeNewPassword.length() < 5) {
            mTxvChangeNofi.setText(R.string.pass_notstrong);
            setAnimNofitication();
        } else if (mEdtChangeConfirmPassword.length() < 5) {
            mTxvChangeNofi.setText(R.string.pass_notstrong);
            setAnimNofitication();
        } else {
            // Check password math
            if (mEdtChangeOldPassword.getText().toString().equals(LoginActivity.mPasswordEcode)) {
                if (mEdtChangeNewPassword.getText().toString().equals(mEdtChangeConfirmPassword.getText().toString())) {
                    getAnimChangePasswordClick();
                    mBtnChangeAccept.startAnimation(AnimationEffect.animHideToZoom(getApplicationContext()));
                    mBtnChangeAccept.startAnimation(AnimationEffect.animCircular(getApplicationContext()));
                    changePassword();
                } else {
                    clearAnimChangePassword();
                    mTxvChangeNofi.setText(R.string.pass_notmatch);
                    setAnimNofitication();
                }
            } else {
                mTxvChangeNofi.setText(R.string.old_pass_incorrected);
                setAnimNofitication();
            }
        }
    }

    @OnClick(R.id.btnChangeCancel)
    public void onClickCancel() {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(Constant.INTENT_TOKEN, LoginActivity.mAccessToken);
        intent.putExtra(Constant.INTENT_USERID, LoginActivity.mUserId);
        startActivity(intent);
        finish();
    }

    public void changePassword() {
        mApiService = ApiUtils.getApiInterface();
        mApiService.changePassword(Constant.AUTH_VALUE + LoginActivity.mAccessToken, LoginActivity.mUserId, mEdtChangeNewPassword.getText().toString()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    // Follow user invite with Fabric
                    Answers.getInstance().logInvite(new InviteEvent().putMethod(Integer.toString(LoginActivity.mUserId)).putMethod("Change password"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                clearAnimChangePassword();
                mTxvChangeNofi.setText(getString(R.string.error_system));
                setAnimNofitication();
            }
        });
    }

    public void getAnimChangePasswordClick () {
        mTxvChangeNofi.setText("");
        mBtnChangeAccept.setBackgroundResource(R.drawable.ic_loading);
        mBtnChangeAccept.setText("");
        mBtnChangeAccept.getLayoutParams().height = 100;
        mBtnChangeAccept.getLayoutParams().width = 100;
    }

    public void clearAnimChangePassword () {
        mBtnChangeAccept.setText(R.string.change_password_change);
        mBtnChangeAccept.getLayoutParams().height = 130;
        mBtnChangeAccept.getLayoutParams().width = 850;
        mBtnChangeAccept.setBackgroundResource(R.drawable.bg_button);
        mBtnChangeAccept.clearAnimation();
    }

    public void setAnimNofitication() {
        mTxvChangeNofi.startAnimation(AnimationEffect.animLeftToRight(getApplicationContext()));
    }
}
