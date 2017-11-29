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
    List<Task> list = new ArrayList<Task>();
    ListView listView;
    CheckBox ckbStatus;
    Button btnMenu;
    TextView txvCompleted,txvAll,txvActive,txvClear,edtAdd;
    TaskAdapter adapter = new TaskAdapter(this,list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        edtAdd = (EditText) findViewById(R.id.edtAdd);
        listView = (ListView) findViewById(R.id.lsvTasks);
        listView.setAdapter(adapter);
        loadTaskActivity();
        eventOnClickAll();
        evenOnClickCompleted();
        eventOnClickAvtive();
        eventOnClickClear();
        eventOnClickAddTask();
    }

    public void eventOnClickAddTask(){
        final String urlAdd = "http://192.168.1.209:8080/tasks";
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.removeAll(list);
                StringRequest request = new StringRequest(Request.Method.POST, urlAdd, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(TaskActivity.this, "Add successful", Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TaskActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
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
                loadTaskActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.password:
                Toast.makeText(TaskActivity.this, "Pass", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logOut:
                Toast.makeText(TaskActivity.this, "Log Out", Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadTaskActivity(){
        JsonArrayRequest jsonreq = new JsonArrayRequest(Request.Method.GET,urlGetAndDelete,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Task tasks = new Task();
                        tasks.setTask(obj.getString("task"));
                        tasks.setStatus(obj.getString("status"));
                        tasks.setTaskId(obj.getString("taskId"));
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
                Toast.makeText(TaskActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TaskActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(TaskActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
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
                adapter.notifyDataSetChanged();
                JsonObjectRequest jsonreq = new JsonObjectRequest(Request.Method.DELETE, urlGetAndDelete, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TaskActivity.this, "Delete completed", Toast.LENGTH_LONG).show();
                    }
                });
                TaskController.getPermission().addToRequestQueue(jsonreq);
            }
        });
    }
}


