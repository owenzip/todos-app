/**
 * TaskAdapter.java
 * Create by Nhut Nguyen
 * Date 31/11/2017
 */
package com.example.gamma.todoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* Class using for Adapter of Testview Task */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class TaskAdapter extends BaseAdapter {

    private List<Task> dataList;
    private LayoutInflater inflater;
    private Activity activity;
    private ApiService mApiService;

    public TaskAdapter(Activity activity, List<Task> dataItem) {
        this.activity = activity;
        this.dataList = dataItem;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int location) {
        return dataList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_task, null);

        final EditText mEdtTasks = (EditText) convertView.findViewById(R.id.edtTasks);
        final CheckBox mCkbStatus = (CheckBox) convertView.findViewById(R.id.ckbStatus);
        final TextView mTxvStatus = (TextView) convertView.findViewById(R.id.txvStatus);
        final Task task = dataList.get(position);

        // Set resource
        mEdtTasks.setLongClickable(true);
        mEdtTasks.setText(task.getTask());
        mCkbStatus.setChecked(Boolean.parseBoolean(task.getStatus().toString()));

        //Event Strike Text when load Task
        if (mCkbStatus.isChecked()) {
            mEdtTasks.setPaintFlags(mEdtTasks.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        mEdtTasks.setInputType(InputType.TYPE_NULL);
        //Event change text color when load Task
        mEdtTasks.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mEdtTasks.setTextColor(Color.GRAY);
                mEdtTasks.setInputType(InputType.TYPE_CLASS_TEXT);
                return true;
            }
        });

        //Even update task when User click Edittext Task
        mEdtTasks.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //When User press key Enter
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    mEdtTasks.setTextColor(Color.parseColor("#465b65"));
                    mEdtTasks.setInputType(InputType.TYPE_NULL);
                    // update task with task = edt.getText

                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateTask(Constant.AUTH_VALUE + LoginActivity.mAccessToken,
                            task.getTaskId().toString(), mEdtTasks.getText().toString(),
                            task.getStatus().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            /*Do not some thing*/
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            /*Do not some thing*/
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        //Event User checked Checkbox
        mCkbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //When User checked
                if (isChecked) {
                    mEdtTasks.setPaintFlags(mEdtTasks.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);   //Set Strike Text
                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateStatus(Constant.AUTH_VALUE + LoginActivity.mAccessToken,
                            task.getTaskId().toString(), Constant.STATUS_TRUE).enqueue(new Callback<Task>() {
                        @Override
                        public void onResponse(Call<Task> call, Response<Task> response) {
                            /*Do not some thing*/
                        }

                        @Override
                        public void onFailure(Call<Task> call, Throwable t) {
                            /*Do not some thing*/
                        }
                    });
                    mCkbStatus.setChecked(true);
                }
                //When user not checked
                else {
                    mEdtTasks.setPaintFlags(0);   //Remove Strike Text
                    mApiService = ApiUtils.getApiInterface();
                    mApiService.updateStatus(Constant.AUTH_VALUE + LoginActivity.mAccessToken,
                            task.getTaskId().toString(), Constant.STATUS_FALSE).enqueue(new Callback<Task>() {
                        @Override
                        public void onResponse(Call<Task> call, Response<Task> response) {
                            /*Do not some thing*/
                        }

                        @Override
                        public void onFailure(Call<Task> call, Throwable t) {
                            /*Do not some thing*/
                        }
                    });
                    mCkbStatus.setChecked(false);
                }
            }
        });
        return convertView;
    }
}