/**
 * TaskActivity.java
 * Create by Nhut Nguyen
 * Date 02/12/2017
 */
package com.example.gamma.todoapp;

import java.util.ArrayList;
import java.util.List;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/* Class using for controller Task */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class TaskActivity extends AppCompatActivity {

    List<Task> mTaskList = new ArrayList<Task>();
    TaskAdapter mTaskAdapter = new TaskAdapter(this, mTaskList);

    @BindView(R.id.edtAdd) EditText mEdtAdd;
    @BindView(R.id.txvNotificationTask) TextView mTxvNofiticationTask;
    @BindView(R.id.lsvTasks) ListView mLsvTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        mLsvTask.setAdapter(mTaskAdapter);
    }
}
