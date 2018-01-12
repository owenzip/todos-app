package com.example.gamma.todoapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.txvProfileUsername) TextView mTxvProfileUsername;
    @BindView(R.id.txvProfileFirstname) TextView mTxvProfileFirstname;
    @BindView(R.id.txvProfileLastname) TextView mTxvProfileLastname;
    @BindView(R.id.txvProfileUserId) TextView mTxvProfileUserId;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        mApiService = ApiUtils.getApiInterface();
        getProfileUser(Constant.AUTH_VALUE + LoginActivity.mAccessToken, LoginActivity.mUserId);
    }

    public void getProfileUser(String accessToken, int userId) {
        mApiService.getProfileUser(accessToken, userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mTxvProfileUsername.setText(response.body().getmUsername());
                mTxvProfileFirstname.setText(response.body().getmFirstname());
                mTxvProfileLastname.setText(response.body().getmLastname());
                mTxvProfileUserId.setText(String.valueOf(response.body().getmUserId()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_system) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnProfileChangePassword)
    public void onClickChangePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
