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

//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/* Class using for Adapter of Testview Task */
public class TaskAdapter extends BaseAdapter {

    @BindView(R.id.edtTasks) EditText mEdtTask;
    @BindView(R.id.txvStatus) TextView mTxvStatus;
    @BindView(R.id.ckbStatus) CheckBox mCkbStatus;

    private List<Task> mTaskList;
    private LayoutInflater mInflater;
    private Activity mActivity;


    public TaskAdapter(Activity activity, List<Task> dataItem) {
        this.mActivity = activity;
        this.mTaskList = dataItem;
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Object getItem(int location) {
        return mTaskList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = mTaskList.get(position);

        if (mInflater == null) {
            mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_task, null);
        }

        mEdtTask.setLongClickable(true);
        mEdtTask.setText(task.getTask());
        mCkbStatus.setChecked(Boolean.parseBoolean(task.getStatus()));
        //Event Strike Text when load Task
        if (mCkbStatus.isChecked()) {
            mEdtTask.setPaintFlags(mEdtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        mTxvStatus.setText(task.getTaskId());
        mEdtTask.setInputType(InputType.TYPE_NULL);
        //Event change text color when load Task
        mEdtTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mEdtTask.setTextColor(Color.GRAY);
                mEdtTask.setInputType(InputType.TYPE_CLASS_TEXT);
                return true;
            }
        });
        return convertView;
    }
}