/**
 * TaskActivity.java
 * Create by Nhut Nguyen
 * Date 02/12/2017
 */
package com.example.gamma.todoapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        loadTaskActivity();
    }

    public void loadTaskActivity() {
        JsonArrayRequest jsonreq = new JsonArrayRequest(Request.Method.GET, String.format(Constant.URL_GET_AND_DELETE, LoginActivity.sUserId), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mTaskList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTask(obj.getString("task"));
                        tasks.setStatus(obj.getString("status"));
                        tasks.setTaskId(obj.getString("taskId"));
                        mTaskList.add(tasks);
                        mTaskAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxvNofiticationTask.setText(getString(R.string.error_system) + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
            }
        };
        TaskController.getPermission().addToRequestQueue(jsonreq);
    }

    @OnClick(R.id.btnMenu)
    public void onClickAddTask(View view) {
        StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_ADD_TASK, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                loadTaskActivity();
                mEdtAdd.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxvNofiticationTask.setText(getString(R.string.error_system) + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("userid", LoginActivity.sUserId);
                parameters.put("task", mEdtAdd.getText().toString());
                return parameters;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
            }
        };
        TaskController.getPermission().addToRequestQueue(request);
    }

    @OnClick(R.id.txvActive)
    public void onClickAvtive(View view) {
        JsonArrayRequest jsonreq = new JsonArrayRequest(String.format(Constant.URL_GET_TASK_ACTIVE, LoginActivity.sUserId), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mTaskList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTask(obj.getString("task"));
                        tasks.setStatus(obj.getString("status"));
                        mTaskList.add(tasks);
                        mTaskAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxvNofiticationTask.setText(getString(R.string.error_system) + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
            }
        };
        TaskController.getPermission().addToRequestQueue(jsonreq);
    }

    @OnClick(R.id.txvAll)
    public void onClickAll(View view) {
        loadTaskActivity();
    }

    @OnClick(R.id.txvCompleted)
    public void onClickCompleted(View view) {
        JsonArrayRequest jsonreq = new JsonArrayRequest(String.format(Constant.URL_GET_TASK_COMPLETED, LoginActivity.sUserId), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mTaskList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTask(obj.getString("task"));
                        tasks.setStatus(obj.getString("status"));
                        mTaskList.add(tasks);
                        mTaskAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxvNofiticationTask.setText(R.string.error_system + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
            }
        };
        TaskController.getPermission().addToRequestQueue(jsonreq);
    }

    @OnClick(R.id.txvClear)
    public void onClickClear(View view) {
        StringRequest request = new StringRequest(Request.Method.DELETE, String.format(Constant.URL_GET_AND_DELETE, LoginActivity.sUserId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Deleted")) {
                    mTaskList.clear();
                    mTaskAdapter.notifyDataSetChanged();
                    loadTaskActivity();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTxvNofiticationTask.setText(getString(R.string.error_system) + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
            }
        };
        TaskController.getPermission().addToRequestQueue(request);
    }
}
