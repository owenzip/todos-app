/**
 * TaskAdapter.java
 * Create by Nhut Nguyen
 * Date 31/11/2017
 */
package com.example.gamma.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/* Class using for Adapter of Testview Task */
public class TaskAdapter extends BaseAdapter {

    @BindView(R.id.txvTaskId)
    TextView mTxvTaskId;
    @BindView(R.id.edtTasks)
    EditText mEdtTask;
    @BindView(R.id.ckbStatus)
    CheckBox mCkbStatus;

    private List<Task> mDataList;
    private LayoutInflater mInflater;
    private Activity mActivity;
    AppCompatActivity mAppcompatActivity;

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
                    mEdtTask.setTextColor(mAppcompatActivity.getResources().getColor(R.color.colorAccept));
                    mEdtTask.setInputType(InputType.TYPE_NULL);
                    mTxvTaskId.setText(task.getTaskId());
                    mEdtTask.setInputType(InputType.TYPE_NULL);
                    //Event change text color when load Task
                    mEdtTask.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            mEdtTask.setTextColor(mAppcompatActivity.getResources().getColor(R.color.colorLongClick));
                            mEdtTask.setInputType(InputType.TYPE_CLASS_TEXT);
                            return true;
                        }
                    });
                }
                return false;
            }
        });
        return convertView;
    }
}