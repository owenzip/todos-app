/**
 * TaskAdapter.java
 * Create by Nhut Nguyen
 * Date 31/11/2017
 */
package com.example.gamma.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for Adapter of Testview Task */
public class TaskAdapter extends BaseAdapter {

    @BindView(R.id.txvTaskId) TextView mTxvTaskId;
    @BindView(R.id.edtTasks) EditText mEdtTask;
    @BindView(R.id.ckbStatus) CheckBox mCkbStatus;

    private Context mContext;
    private Intent mIntent;
    private List<Task> mDataList;
    private LayoutInflater mInflater;
    private Activity mActivity;
    AppCompatActivity mAppcompatActivity;
    ApiService mApiService;

    public TaskAdapter(Activity activity, List<Task> dataItem) {
        this.mActivity = activity;
        this.mDataList = dataItem;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int location) {
        return mDataList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (mInflater == null) {
            mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_task, null);
        }


        ButterKnife.bind(this, convertView);
        final Task task = mDataList.get(position);

        mEdtTask.setLongClickable(true); // Turn on longClick
        mEdtTask.setText(task.getTask());
        mTxvTaskId.setText(task.getTaskId().toString());
        mCkbStatus.setChecked(Boolean.parseBoolean(task.getStatus())); // Parse boolean set checkBox
        mEdtTask.setInputType(InputType.TYPE_NULL); // Turn off input text
        // Set Strike Text with status checked
        if (mCkbStatus.isChecked()) {
            mEdtTask.setPaintFlags(mEdtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // Event long click mEdtTask for logic update
        mEdtTask.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //When User press key Enter
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //mEdtTask.setTextColor(mAppcompatActivity.getResources().getColor(R.color.colorAccept));
                    mEdtTask.setInputType(InputType.TYPE_NULL);
                    //Event change text color when load Task
                    mEdtTask.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            //mEdtTask.setTextColor(mAppcompatActivity.getResources().getColor(R.color.colorLongClick));
                            mEdtTask.setInputType(InputType.TYPE_CLASS_TEXT);
                            return true;
                        }
                    });
                }
                return false;
            }
        });

        // Event user long click edtTask
        mEdtTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
               // mEdtTask.setTextColor(mAppcompatActivity.getResources().getColor(R.color.colorLongClick));
                mEdtTask.setInputType(InputType.TYPE_CLASS_TEXT);
                return true;
            }
        });

        // Event update Task when press key enter
        mEdtTask.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //mEdtTask.setTextColor(mAppcompatActivity.getResources().getColor(R.color.colorText));
                    mEdtTask.setInputType(InputType.TYPE_NULL);
                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateTask(Constant.AUTH_VALUE + LoginActivity.mAccessToken, task.getTaskId().toString(),mEdtTask.getText().toString(), task.getStatus().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            // do some things
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            // do some things
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        // Event combobox checked
        mCkbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mEdtTask.setPaintFlags(mEdtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateStatus(Constant.AUTH_VALUE + LoginActivity.mAccessToken,task.getTaskId().toString(),Constant.STATUS_TRUE).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                } else {
                    mEdtTask.setPaintFlags(0);
                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateStatus(Constant.AUTH_VALUE + LoginActivity.mAccessToken,task.getTaskId().toString(),Constant.STATUS_FALSE).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }
        });
                return convertView;
    }
}