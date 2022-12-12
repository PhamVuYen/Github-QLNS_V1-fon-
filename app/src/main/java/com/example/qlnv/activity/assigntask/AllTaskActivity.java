package com.example.qlnv.activity.assigntask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlnv.Injector;
import com.example.qlnv.R;
import com.example.qlnv.activity.manageuser.EditEmployeeActivity;
import com.example.qlnv.activity.manageuser.ManageUserActivity;
import com.example.qlnv.adapter.AdapterAllTask;
import com.example.qlnv.adapter.AdapterTask;
import com.example.qlnv.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllTaskActivity extends AppCompatActivity {
    RecyclerView rvTask;
    Button btnAssignTask;
    ArrayList<Task> tasks = new ArrayList<>();
    AdapterAllTask adapterAllTask ;
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tasks_details);
        initView();
        id = getIntent().getStringExtra("idTask");
        adapterAllTask = new AdapterAllTask(tasks,AllTaskActivity.this);
        rvTask.setAdapter(adapterAllTask);
        rvTask.setLayoutManager(new LinearLayoutManager(AllTaskActivity.this));
        queryAllTask(id);
    }

    public void queryAllTask(String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_EDIT_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {

                    } catch (Exception e) {
                        Toast.makeText(AllTaskActivity.this, "Some error", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err",error+"");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("CreateBy",id);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void initView() {
        rvTask = findViewById(R.id.rv_task);
        btnAssignTask = findViewById(R.id.assign_new_task);
    }

}
