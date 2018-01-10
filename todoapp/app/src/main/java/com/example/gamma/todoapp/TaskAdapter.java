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
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

/* Class using for Adapter of Testview Task */
public class TaskAdapter extends BaseAdapter {

//    @BindView(R.id.edtTasks) EditText mEdtTask;
//    @BindView(R.id.txvStatus) TextView mTxvStatus;
//    @BindView(R.id.ckbStatus) CheckBox mCkbStatus;
//
//    private List<Task> mTaskList;
//    private LayoutInflater mInflater;
//    private Activity mActivity;
//
//    public TaskAdapter(Activity activity, List<Task> dataItem) {
//        this.mActivity = activity;
//        this.mTaskList = dataItem;
//    }
//
//    @Override
//    public int getCount() {
//        return mTaskList.size();
//    }
//
//    @Override
//    public Object getItem(int location) {
//        return mTaskList.get(location);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        final Task task = mTaskList.get(position);
//
//        if (mInflater == null) {
//            mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//        if (convertView == null) {
//            convertView = mInflater.inflate(R.layout.item_task, null);
//            ButterKnife.bind(this,convertView);
//        }
//
//
//        mEdtTask.setLongClickable(true);
//        mEdtTask.setText(task.getTask());
//        mCkbStatus.setChecked(Boolean.parseBoolean(task.getStatus()));
//        //Event Strike Text when load Task
//        if (mCkbStatus.isChecked()) {
//            mEdtTask.setPaintFlags(mEdtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        }
//
//        mTxvStatus.setText(task.getTaskId());
//        mEdtTask.setInputType(InputType.TYPE_NULL);
//        //Event change text color when load Task
//        mEdtTask.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                mEdtTask.setTextColor(Color.GRAY);
//                mEdtTask.setInputType(InputType.TYPE_CLASS_TEXT);
//                return true;
//            }
//        });
//        return convertView;
//    }

    private List<Task> dataList;
    private LayoutInflater inflater;
    private Activity activity;

    public TaskAdapter(Activity activity, List<Task> dataItem){
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

        final TextView txvTaskId = (TextView) convertView.findViewById(R.id.txvTaskId);
        final EditText edtTaskId = (EditText) convertView.findViewById(R.id.edtTasks);
        final CheckBox ckbStatus = (CheckBox) convertView.findViewById(R.id.ckbStatus);

        final Task task = dataList.get(position);

        //edtTask.setLongClickable(true);
        edtTaskId.setText(task.getTask());
        ckbStatus.setChecked(Boolean.parseBoolean(task.getStatus()));

//        //Event Strike Text when load Task
        if (ckbStatus.isChecked()){
            edtTaskId.setPaintFlags(edtTaskId.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        edtTaskId.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //When User press key Enter
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    edtTaskId.setTextColor(Color.parseColor("#465b65"));
                    edtTaskId.setInputType(InputType.TYPE_NULL);
                    txvTaskId.setText(task.getTaskId());
                    edtTaskId.setInputType(InputType.TYPE_NULL);
                    //Event change text color when load Task
                    edtTaskId.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            edtTaskId.setTextColor(Color.GRAY);
                            edtTaskId.setInputType(InputType.TYPE_CLASS_TEXT);
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