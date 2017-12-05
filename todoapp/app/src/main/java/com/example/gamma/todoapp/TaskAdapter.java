/*
 * TaskAdapter.java
 * Create by Nhut Nguyen
 * Date 31/11/2017
 */

package com.example.gamma.todoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Class using for Adapter of Testview Task*/
public class TaskAdapter extends BaseAdapter {

    private List<Task> dataList;
    private LayoutInflater inflater;
    private Activity activity;
    String urlAdd = "http://192.168.1.209:8080/tasks";

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

        final EditText edtTask = (EditText) convertView.findViewById(R.id.edtTasks);
        final CheckBox ckbStatus = (CheckBox) convertView.findViewById(R.id.ckbStatus);
        final TextView txvStatus = (TextView) convertView.findViewById(R.id.txvStatus);
        final Task task = dataList.get(position);

        edtTask.setLongClickable(true);
        edtTask.setText(task.getTask());
        ckbStatus.setChecked(Boolean.parseBoolean(task.getStatus()));

        //Event Strike Text when load Task
        if (ckbStatus.isChecked()){
            edtTask.setPaintFlags(edtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        txvStatus.setText(task.getTaskId());
        edtTask.setInputType(InputType.TYPE_NULL);
        //Event change text color when load Task
        edtTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                edtTask.setTextColor(Color.GRAY);
                edtTask.setInputType(InputType.TYPE_CLASS_TEXT);
                return true;
            }
        });

        //Even update task when User click Edittext Task
        edtTask.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //When User press key Enter
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    edtTask.setTextColor(Color.parseColor("#465b65"));
                    edtTask.setInputType(InputType.TYPE_NULL);
                    String urlUpdateTask = String.format("http://192.168.1.209:8080/tasks/%1$s",txvStatus.getText().toString());
                    StringRequest request = new StringRequest(Request.Method.PUT, urlUpdateTask, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            //Can't return because Data saved
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Can't nofitication because had nofitication at LoginActivity
                        }
                    }) {
                        //Get task, status from Adapter and keep stable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("task", edtTask.getText().toString());
                            parameters.put("status", task.getStatus().toString());
                            return parameters;
                        }
                    };
                    TaskController.getPermission().addToRequestQueue(request);
                    return true;
                }
                return false;
            }
        });

        //Event User checked Checkbox
        ckbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //When User checked
                if (isChecked){
                    edtTask.setPaintFlags(edtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);   //Set Strike Text
                    StringRequest request = new StringRequest(Request.Method.PUT, urlAdd, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            //Can't return because Data saved
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Can't nofitication because had nofitication at LoginActivity
                        }
                    }) {
                        //Get TaskId, Status from Adapter and keep stable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("taskid", txvStatus.getText().toString());
                            parameters.put("status", "true");
                            return parameters;
                        }
                    };
                    TaskController.getPermission().addToRequestQueue(request);
                }
                //When user not checked
                else {
                    edtTask.setPaintFlags(0);   //Remove Strike Text
                    StringRequest request = new StringRequest(Request.Method.PUT, urlAdd, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {

                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        //Get TaskId, Status from Adapter and keep stable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("taskid",txvStatus.getText().toString());
                            parameters.put("status", "false");
                            return parameters;
                        }
                    };
                    TaskController.getPermission().addToRequestQueue(request);
                }
            }
        });
        return convertView;
    }
}

