package com.example.gamma.todoapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.edtChangeOldPassword) EditText mEdtChangeOldPassword;
    @BindView(R.id.edtChangeNewPassword) EditText mEdtChangeNewPassword;
    @BindView(R.id.edtChangeConfirmPassword) EditText mEdtChangeConfirmPassword;
    @BindView(R.id.txvChangeNofi) TextView mTxvChangeNofi;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    // Event click button Change Password
    @OnClick(R.id.btnChangeAccept)
    public void changePassword(View view) {
        if (mEdtChangeOldPassword.length() < 5) {
            mTxvChangeNofi.setText(R.string.old_pass_required);
        } else if (mEdtChangeNewPassword.length() < 5) {
            mTxvChangeNofi.setText(R.string.pass_notstrong);
        } else if (mEdtChangeConfirmPassword.length() < 5) {
            mTxvChangeNofi.setText(R.string.pass_notstrong);
        } else {
            if (mEdtChangeOldPassword.getText().toString().equals(LoginActivity.mPasswordEcode)) {
                if (mEdtChangeNewPassword.getText().toString().equals(mEdtChangeConfirmPassword.getText().toString())) {
                    changePassword();
                } else {
                    mTxvChangeNofi.setText(R.string.pass_notmatch);
                }
            } else {
                mTxvChangeNofi.setText(R.string.old_pass_incorrected);
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
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mTxvChangeNofi.setText(getString(R.string.error_system) + t);
            }
        });
    }
}
