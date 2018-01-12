/**
 * TaskActivity.java
 * Create by Nhut Nguyen
 * Date 02/12/2017
 */
package com.example.gamma.todoapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
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
    @BindView(R.id.lsvTasks) ListView mLsvTask;
    @BindView(R.id.layTabClear) LinearLayout mLayTabClear;
    @BindView(R.id.layTabAll) LinearLayout mLayTabAll;
    @BindView(R.id.layTabActive) LinearLayout mLayTabActive;
    @BindView(R.id.layTabCompleted) LinearLayout mLayTabCompleted;
    @BindView(R.id.btnMenu)
    Button mBtnMenu;

    List<Task> mListTask = new ArrayList<Task>();
    TaskAdapter mTaskAdapter = new TaskAdapter(TaskActivity.this, mListTask);
    ApiService mApiService;
    String mAccessToken;
    int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        mLsvTask.setAdapter(mTaskAdapter);

        // Get User Id & Access Token
        Intent intent = getIntent();
        mAccessToken = intent.getStringExtra(Constant.INTENT_TOKEN);
        mUserId = intent.getExtras().getInt(Constant.INTENT_USERID);
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
                        mTaskAdapter.notifyDataSetChanged();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_system) + ex, Toast.LENGTH_SHORT).show();
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
        mLayTabAll.setVisibility(View.VISIBLE);
        mLayTabCompleted.setVisibility(View.GONE);
        mLayTabActive.setVisibility(View.GONE);
        mLayTabClear.setVisibility(View.GONE);
        getAllTask();
    }

    @OnClick(R.id.txvActive)
    public void onClickTxvActive(View view) {
        mLayTabAll.setVisibility(View.GONE);
        mLayTabClear.setVisibility(View.GONE);
        mLayTabActive.setVisibility(View.VISIBLE);
        mLayTabCompleted.setVisibility(View.GONE);

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
                        mTaskAdapter.notifyDataSetChanged();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_system) + ex, Toast.LENGTH_SHORT).show();
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
        mLayTabCompleted.setVisibility(View.VISIBLE);
        mLayTabAll.setVisibility(View.GONE);
        mLayTabClear.setVisibility(View.GONE);
        mLayTabActive.setVisibility(View.GONE);

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
                        mTaskAdapter.notifyDataSetChanged();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_system) + ex, Toast.LENGTH_SHORT).show();
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
        mLayTabClear.setVisibility(View.VISIBLE);
        mLayTabAll.setVisibility(View.GONE);
        mLayTabCompleted.setVisibility(View.GONE);
        mLayTabActive.setVisibility(View.GONE);

        mListTask.clear();
        mTaskAdapter.notifyDataSetChanged();
        mApiService.deleteTaskCompleted(Constant.AUTH_VALUE + mAccessToken, mUserId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), getString(R.string.clear_success), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_system) + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void onClickAdd(View view) {
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
                return true;
            case R.id.logOut:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                return true;
        }
        return false;
    }
}
