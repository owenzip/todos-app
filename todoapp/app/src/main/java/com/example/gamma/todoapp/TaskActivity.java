/**
 * TaskActivity.java
 * Create by Nhut Nguyen
 * Date 02/12/2017
 */
package com.example.gamma.todoapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for controller Task */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class TaskActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.edtAdd) EditText mEdtAdd;
    @BindView(R.id.btnMenu) Button mBtnMenu;
    @BindView(R.id.txvClear) TextView mTxvClear;
    @BindView(R.id.txvCompleted) TextView mTxvCompleted;
    @BindView(R.id.txvActive) TextView mTxvActive;
    @BindView(R.id.txvAll) TextView mTxvAll;

    private List<Task> mListTask = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    ApiService mApiService;
    String mAccessToken;
    int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        mRecyclerView = findViewById(R.id.rclTasks);
        mTaskAdapter = new TaskAdapter(this, mListTask);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTaskAdapter);

        // Get User Id & Access Token
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.INTENT_TOKEN) && intent.hasExtra(Constant.INTENT_USERID)) {
            mAccessToken = intent.getStringExtra(Constant.INTENT_TOKEN);
            mUserId = intent.getExtras().getInt(Constant.INTENT_USERID);
        } else {
            Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show();
        }
        mApiService = ApiUtils.getApiInterface();
        getAllTask();
    }

    public void getAllTask() {
        mListTask.clear();
        mTaskAdapter.notifyDataSetChanged();
        mApiService.getAllTask(Constant.AUTH_VALUE + mAccessToken, mUserId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray ja = new JSONArray(response.body().string());
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTaskId(jo.getInt(Constant.TASK_ID));
                        tasks.setTask(jo.getString(Constant.TASK_TASK));
                        tasks.setStatus(jo.getString(Constant.TASK_STATUS));
                        mListTask.add(tasks);
                    }
                    mTaskAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_system) + ex, Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_system) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.txvAll)
    public void onClickTxvAll(View view) {
        getAllTask();
        mTxvAll.setTypeface(Typeface.DEFAULT_BOLD);
        mTxvActive.setTypeface(Typeface.DEFAULT);
        mTxvCompleted.setTypeface(Typeface.DEFAULT);
        mTxvClear.setTypeface(Typeface.DEFAULT);
    }

    @OnClick(R.id.txvActive)
    public void onClickTxvActive(View view) {
        mTxvAll.setTypeface(Typeface.DEFAULT);
        mTxvActive.setTypeface(Typeface.DEFAULT_BOLD);
        mTxvCompleted.setTypeface(Typeface.DEFAULT);
        mTxvClear.setTypeface(Typeface.DEFAULT);

        mListTask.clear();
        mTaskAdapter.notifyDataSetChanged();
        mApiService.getTaskActive(Constant.AUTH_VALUE + mAccessToken, mUserId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    mListTask.clear();
                    JSONArray ja = new JSONArray(response.body().string());
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTaskId(jo.getInt(Constant.TASK_ID));
                        tasks.setTask(jo.getString(Constant.TASK_TASK));
                        tasks.setStatus(jo.getString(Constant.TASK_STATUS));
                        mListTask.add(tasks);
                    }
                    mTaskAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_system) + ex, Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_system) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.txvCompleted)
    public void onClickCompleted(View view) {
        mTxvAll.setTypeface(Typeface.DEFAULT);
        mTxvActive.setTypeface(Typeface.DEFAULT);
        mTxvCompleted.setTypeface(Typeface.DEFAULT_BOLD);
        mTxvClear.setTypeface(Typeface.DEFAULT);

        mListTask.clear();
        mTaskAdapter.notifyDataSetChanged();
        mApiService.getTaskCompleted(Constant.AUTH_VALUE + mAccessToken, mUserId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray ja = new JSONArray(response.body().string());
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTaskId(jo.getInt(Constant.TASK_ID));
                        tasks.setTask(jo.getString(Constant.TASK_TASK));
                        tasks.setStatus(jo.getString(Constant.TASK_STATUS));
                        mListTask.add(tasks);
                    }
                    mTaskAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_system) + ex, Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_system) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.txvClear)
    public void onClickClear(View view) {
        mTxvAll.setTypeface(Typeface.DEFAULT);
        mTxvActive.setTypeface(Typeface.DEFAULT);
        mTxvCompleted.setTypeface(Typeface.DEFAULT);
        mTxvClear.setTypeface(Typeface.DEFAULT_BOLD);

        mListTask.clear();
        mTaskAdapter.notifyDataSetChanged();
        mApiService.deleteTaskCompleted(Constant.AUTH_VALUE + mAccessToken, mUserId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), getString(R.string.clear_success), Toast.LENGTH_SHORT).show();
                getAllTask();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_system) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void onClickAdd(View view) {
        if (mEdtAdd.length() < 1) {
            Toast.makeText(getApplicationContext(), getString(R.string.task_required), Toast.LENGTH_SHORT).show();
        } else {
            mListTask.clear();
            mTaskAdapter.notifyDataSetChanged();
            mApiService.addTask(Constant.AUTH_VALUE + mAccessToken, mUserId, mEdtAdd.getText().toString()).enqueue(new Callback<Task>() {
                @Override
                public void onResponse(Call<Task> call, Response<Task> response) {
                    getAllTask();
                    Toast.makeText(getApplicationContext(), getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                    mEdtAdd.setText("");
                }

                @Override
                public void onFailure(Call<Task> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_system) + t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @OnClick(R.id.btnMenu)
    public void onClickMenu(View view) {
        if (view == mBtnMenu) {
            showMenu(view);
        }
    }

    public void showMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.task_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.password:
                Intent intentUserInfor = new Intent(this, UserInfoActivity.class);
                startActivity(intentUserInfor);
                finish();
                return true;
            case R.id.logOut:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                return true;
        }
        return false;
    }
}
