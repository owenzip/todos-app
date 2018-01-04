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
import android.util.Base64;
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

/* Class using for controller Task */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class TaskActivity extends AppCompatActivity {

    String userId = LoginActivity.userId;
    String urlGetAndDelete = String.format(Constant.URL_GET_AND_DELETE, userId);
    String urlFindCompleted = String.format(Constant.URL_GET_TASK_COMPLETED, userId);
    String urlFindActive = String.format(Constant.URL_GET_TASK_ACTIVE, userId);
    List<Task> list = new ArrayList<Task>();
    TaskAdapter adapter = new TaskAdapter(this, list);

    @BindView(R.id.edtAdd) EditText edtAdd;
    @BindView(R.id.txvCompleted) TextView txvCompleted;
    @BindView(R.id.txvAll) TextView txvAll;
    @BindView(R.id.txvActive) TextView txvActive;
    @BindView(R.id.txvClear) TextView txvClear;
    @BindView(R.id.txvNotificationTask) TextView txvNofiticationTask;
    @BindView(R.id.btnMenu) Button btnMenu;
    @BindView(R.id.lsvTasks) ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listView.setAdapter(adapter);
        loadTaskActivity();
        eventOnClickAll();
        evenOnClickCompleted();
        eventOnClickAvtive();
        eventOnClickClear();
        eventOnClickAddTask();
    }

    public void loadTaskActivity() {
        JsonArrayRequest jsonreq = new JsonArrayRequest(Request.Method.GET, urlGetAndDelete, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                list.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTask(obj.getString("task"));
                        tasks.setStatus(obj.getString("status"));
                        tasks.setTaskId(obj.getString("taskId"));
                        list.add(tasks);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txvNofiticationTask.setText("Network Disconnection");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
            }
        };
        TaskController.getPermission().addToRequestQueue(jsonreq);
    }

    public void eventOnClickAddTask() {

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, Constant.URL_ADD_TASK, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loadTaskActivity();
                        edtAdd.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txvNofiticationTask.setText("Some error occurred");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("userid", userId);
                        parameters.put("task", edtAdd.getText().toString());
                        return parameters;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
                    }
                };
                TaskController.getPermission().addToRequestQueue(request);
            }
        });
    }

    public void eventOnClickAvtive() {
        txvActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonArrayRequest jsonreq = new JsonArrayRequest(urlFindActive, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Task tasks = new Task();
                                tasks.setTask(obj.getString("task"));
                                tasks.setStatus(obj.getString("status"));
                                list.add(tasks);
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txvNofiticationTask.setText("Some error occurred");
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
                    }
                };
                TaskController.getPermission().addToRequestQueue(jsonreq);
            }
        });
    }

    public void eventOnClickAll() {
        txvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTaskActivity();
            }
        });
    }

    public void evenOnClickCompleted() {
        txvCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonArrayRequest jsonreq = new JsonArrayRequest(urlFindCompleted, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Task tasks = new Task();
                                tasks.setTask(obj.getString("task"));
                                tasks.setStatus(obj.getString("status"));
                                list.add(tasks);
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txvNofiticationTask.setText("Some error occurred");
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
                    }
                };
                TaskController.getPermission().addToRequestQueue(jsonreq);

            }
        });
    }

    public void eventOnClickClear() {
        txvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest request = new StringRequest(Request.Method.DELETE, urlGetAndDelete, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Deleted")) {
                            list.clear();
                            adapter.notifyDataSetChanged();
                            loadTaskActivity();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txvNofiticationTask.setText("Some error occurred");
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return Authentication.createBasicAuthHeader(Constant.BASIC_AUTH_USERNAME, Constant.BASIC_AUTH_PASSWORD);
                    }
                };
                TaskController.getPermission().addToRequestQueue(request);
            }
        });
    }
}
