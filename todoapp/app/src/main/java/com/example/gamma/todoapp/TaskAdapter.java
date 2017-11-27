package com.example.gamma.todoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private List<Task> dataList;
    private LayoutInflater inflater;
    private Activity activity;
    public String valueTaskId;

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
            convertView = inflater.inflate(R.layout.task_layout, null);

        EditText edtTask = (EditText) convertView.findViewById(R.id.edtTasks);
        CheckBox ckbStatus = (CheckBox) convertView.findViewById(R.id.ckbStatus);
        TextView txvStatus = (TextView) convertView.findViewById(R.id.txvStatus);
        Task task = dataList.get(position);
        edtTask.setText(task.getTask());
        ckbStatus.setChecked(Boolean.parseBoolean(task.getStatus()));
        txvStatus.setText(task.getTaskId());
        return convertView;
    }
}
