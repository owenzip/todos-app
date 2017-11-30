package com.example.gamma.todoapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class TaskActivity extends AppCompatActivity {

    String userId = LoginActivity.userId;
    String urlGetAndDelete = String.format("http://192.168.1.209:8080/users/%1$s/tasks",userId);
    String urlFindCompleted = String.format("http://192.168.1.209:8080/users/%1$s/tasks/true",userId);
    String urlFindActive = String.format("http://192.168.1.209:8080/users/%1$s/tasks/false",userId);
    TextView txvCompleted,txvAll,txvActive,txvClear,edtAdd,txvNofiticationTask;
    List<Task> list = new ArrayList<Task>();
    ListView listView; Button btnMenu;
    TaskAdapter adapter = new TaskAdapter(this,list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        edtAdd = (EditText) findViewById(R.id.edtAdd);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        listView = (ListView) findViewById(R.id.lsvTasks);
        txvNofiticationTask = (TextView) findViewById(R.id.txvNotificationTask);
        listView.setAdapter(adapter);

        loadTaskActivity();
        eventOnClickAll();
        evenOnClickCompleted();
        eventOnClickAvtive();
        eventOnClickClear();
        eventOnClickAddTask();
    }

    public void loadTaskActivity(){
        JsonArrayRequest jsonreq = new JsonArrayRequest(Request.Method.GET,urlGetAndDelete,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                list.removeAll(list);
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
        });
        TaskController.getPermission().addToRequestQueue(jsonreq);
    }

    public void eventOnClickAddTask(){

        final String urlAdd = "http://192.168.1.209:8080/tasks";
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest request = new StringRequest(Request.Method.POST, urlAdd, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        loadTaskActivity();
                        edtAdd.setText("");
                    }
                },new Response.ErrorListener(){
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
                };
                TaskController.getPermission().addToRequestQueue(request);
            }
        });
    }



    public void eventOnClickAvtive(){
        txvActive = (TextView) findViewById(R.id.txvActive);
        txvActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonArrayRequest jsonreq = new JsonArrayRequest(urlFindActive,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        list.removeAll(list);
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
                loadTaskActivity();
            }
        });
    }

    public void evenOnClickCompleted(){

        txvCompleted = (TextView) findViewById(R.id.txvCompleted);
        txvCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonArrayRequest jsonreq = new JsonArrayRequest(urlFindCompleted,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        list.removeAll(list);
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

                StringRequest request = new StringRequest(Request.Method.DELETE,urlGetAndDelete, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Deleted")){
                            list.removeAll(list);
                            adapter.notifyDataSetChanged();
                            loadTaskActivity();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txvNofiticationTask.setText("Some error occurred");
                    }
                });
                TaskController.getPermission().addToRequestQueue(request);
            }
        });
    }
}


