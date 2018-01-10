/**
 * TaskActivity.java
 * Create by Nhut Nguyen
 * Date 02/12/2017
 */
package com.example.gamma.todoapp;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for controller Task */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class TaskActivity extends AppCompatActivity {

    @BindView(R.id.edtAdd)
    EditText mEdtAdd;
    @BindView(R.id.txvNotificationTask)
    TextView mTxvNofiticationTask;
    @BindView(R.id.lsvTasks)
    ListView mLsvTask;

    List<Task> mListTask = new ArrayList<Task>();
    TaskAdapter mTaskAdapter = new TaskAdapter(this, mListTask);
    ApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        mLsvTask.setAdapter(mTaskAdapter);

        Intent intent = getIntent();
        String mAccessToken = intent.getStringExtra(Constant.INTENT_TOKEN);
        int mUserId = intent.getExtras().getInt(Constant.INTENT_USERID);
        mApiService = ApiUtils.getApiInterface();

        Task task = new Task();
        task.setTaskId("3232");
        task.setTask("Tasasasas");
        task.setStatus("true");

        Task task2 = new Task();
        task2.setTaskId("7848");
        task2.setTask("utiuer");
        task2.setStatus("false");

        mListTask.add(task);
        mListTask.add(task2);

        //loadAllTask(Constant.AUTH_VALUE + mAccessToken, mUserId);

    }

    public void loadAllTask(String accessToken, int userId) {
        mApiService.getTaskByUserId(accessToken, userId).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                // Set in listview
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {

            }
        });
    }
}
