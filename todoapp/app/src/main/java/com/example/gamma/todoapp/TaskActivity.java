package com.example.gamma.todoapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TaskActivity extends AppCompatActivity {

    private String tag = TaskActivity.class.getSimpleName();
    private String urlFindAndClearAll= "http://192.168.1.209:8080/users/10/tasks";
    private String urlFindCompleted = "http://192.168.1.209:8080/users/10/tasks/true";
    private String urlFindActive = "http://192.168.1.209:8080/users/10/tasks/false";
    private String urlPutStatus = "http://localhost:8080/tasks";
    private List<Task> list = new ArrayList<Task>();
    private ListView listView;
    private CheckBox ckbStatus;
    private TextView txvCompleted,txvAll,txvActive,txvClear;
    private TaskAdapter adapter = new TaskAdapter(this,list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listView = (ListView) findViewById(R.id.lsvTasks);
        listView.setAdapter(adapter);
        loadTaskActivity();
        eventOnClickAll();
        evenOnClickCompleted();
        eventOnClickAvtive();
        eventOnClickClear();
        //eventOnClickStatus();
    }

    public void loadTaskActivity(){
        JsonArrayRequest jsonreq = new JsonArrayRequest(urlFindAndClearAll,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTask(obj.getString("task"));
                        tasks.setStatus(obj.getString("status"));
                        //tasks.setTaskId(obj.getString("taskId"));
                        list.add(tasks);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder add = new AlertDialog.Builder(TaskActivity.this);
                add.setMessage(error.getMessage()).setCancelable(true);
                AlertDialog alert = add.create();
                alert.setTitle("Disconnected");
                alert.show();
            }
        });
        TaskController.getPermission().addToRequestQueue(jsonreq);
    }

    public void eventOnClickAvtive(){
        txvActive = (TextView) findViewById(R.id.txvActive);
        txvActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.removeAll(list);
                adapter.notifyDataSetChanged();
                JsonArrayRequest jsonreq = new JsonArrayRequest(urlFindActive,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Task tasks = new Task();
                                tasks.setTask(obj.getString("task"));
                                tasks.setStatus(obj.getString("status"));
                                list.add(tasks);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder add = new AlertDialog.Builder(TaskActivity.this);
                        add.setMessage(error.getMessage()).setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("Disconnected");
                        alert.show();
                    }
                });
                TaskController.getPermission().addToRequestQueue(jsonreq);
            }
        });
    }
    public void eventOnClickAll(){

        txvAll = (TextView) findViewById(R.id.txvAll);
        txvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.removeAll(list);
                adapter.notifyDataSetChanged();
                loadTaskActivity();
            }
        });
    }

    public void evenOnClickCompleted(){

        txvCompleted = (TextView) findViewById(R.id.txvCompleted);
        txvCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.removeAll(list);
                adapter.notifyDataSetChanged();
                JsonArrayRequest jsonreq = new JsonArrayRequest(urlFindCompleted,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Task tasks = new Task();
                                tasks.setTask(obj.getString("task"));
                                tasks.setStatus(obj.getString("status"));
                                list.add(tasks);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder add = new AlertDialog.Builder(TaskActivity.this);
                        add.setMessage(error.getMessage()).setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("Disconnected");
                        alert.show();
                    }
                });
                TaskController.getPermission().addToRequestQueue(jsonreq);
            }
        });
    }

    public void eventOnClickClear(){
        txvClear = (TextView) findViewById(R.id.txvClear);
        txvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.DELETE, urlFindAndClearAll, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder add = new AlertDialog.Builder(TaskActivity.this);
                        add.setMessage(error.getMessage()).setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("Disconnected");
                        alert.show();
                    }
                });
                TaskController.getPermission().addToRequestQueue(jsonreq);
            }
        });
    }
    /*
    public void eventOnClickStatus(){
        ckbStatus = (CheckBox) findViewById(R.id.ckbStatus);
        ckbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    checkBoxChecked();
                else
                    checkBoxNoneChecked();
            }
        });
    }*/

    public void checkBoxChecked(){
        StringRequest putRequest = new StringRequest(Request.Method.PUT, urlPutStatus, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d("Reponse",response);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder add = new AlertDialog.Builder(TaskActivity.this);
                add.setMessage(error.getMessage()).setCancelable(true);
                AlertDialog alert = add.create();
                alert.setTitle("Disconnected");
                alert.show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                adapter.notifyDataSetChanged();
                Task tasks = new Task();
                Map<String, String>  params = new HashMap<String, String>();
                params.put("taskId",tasks.getTaskId());
                params.put("status","true");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(putRequest);
        adapter.notifyDataSetChanged();
    }

    public void checkBoxNoneChecked(){
        StringRequest putRequest = new StringRequest(Request.Method.PUT, urlPutStatus,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Reponse",response);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder add = new AlertDialog.Builder(TaskActivity.this);
                        add.setMessage(error.getMessage()).setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("Disconnected");
                        alert.show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                adapter.notifyDataSetChanged();
                Task tasks = new Task();
                Map<String, String>  params = new HashMap<String, String>();
                params.put("taskId",tasks.getTaskId());
                params.put("status","false");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(putRequest);
        adapter.notifyDataSetChanged();
    }
}


