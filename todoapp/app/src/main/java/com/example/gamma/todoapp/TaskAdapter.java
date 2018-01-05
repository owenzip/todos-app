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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
    AppCompatActivity appCompatActivity;

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
        //Even update task when User click Edittext Task
        mEdtTask.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //When User press key Enter
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    mEdtTask.setTextColor(appCompatActivity.getResources().getColor(R.color.colorText));
                    mEdtTask.setInputType(InputType.TYPE_NULL);
                    String urlUpdateTask = String.format(Constant.URL_UPDATE_TASK, mTxvStatus.getText().toString());
                    StringRequest request = new StringRequest(Request.Method.PUT, urlUpdateTask, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Can't return because Data saved
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Can't nofitication because had nofitication at LoginActivity
                        }
                    }) {
                        //Get task, status from Adapter and keep stable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("task", mEdtTask.getText().toString());
                            parameters.put("status", task.getStatus().toString());
                            return parameters;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
                        }
                    };
                    TaskController.getPermission().addToRequestQueue(request);
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
                    mEdtTask.setPaintFlags(mEdtTask.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);   //Set Strike Text
                    StringRequest request = new StringRequest(Request.Method.PUT, Constant.URL_ADD_TASK, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Can't return because Data saved
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Can't nofitication because had nofitication at LoginActivity
                        }
                    }) {
                        //Get TaskId, Status from Adapter and keep stable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("taskid", mTxvStatus.getText().toString());
                            parameters.put("status", "true");
                            return parameters;
                        }
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
                        }
                    };
                    TaskController.getPermission().addToRequestQueue(request);
                }
                //When user not checked
                else {
                    mEdtTask.setPaintFlags(0);   //Remove Strike Text
                    StringRequest request = new StringRequest(Request.Method.PUT, Constant.URL_ADD_TASK, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Can't return because Data saved
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Can't nofitication because had nofitication at LoginActivity
                        }
                    }) {
                        //Get TaskId, Status from Adapter and keep stable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("taskid", mTxvStatus.getText().toString());
                            parameters.put("status", "false");
                            return parameters;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
                        }
                    };
                    TaskController.getPermission().addToRequestQueue(request);
                }
            }
        });
        return convertView;
    }
}
