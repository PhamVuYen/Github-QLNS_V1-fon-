package com.example.qlnv.activity.assigntask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlnv.Injector;
import com.example.qlnv.R;
import com.example.qlnv.activity.manageuser.ManageUserActivity;
import com.example.qlnv.adapter.AdapterTask;
import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;
import com.example.qlnv.model.Task;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskActivity extends AppCompatActivity {
    RecyclerView rvTask;
    Employee employee;
    ArrayList<Task> tasks = new ArrayList<>();
    AdapterTask adapterTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initView();
        employee = Injector.getEmployee();
        getTaskOfUser(employee);
        adapterTask = new AdapterTask(tasks,getApplicationContext());
    }


    void initView() {
        rvTask = findViewById(R.id.rv_task);
    }

    private void getTaskOfUser(Employee employee) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_QUERY_TASK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        for (int i = 0 ; i < jsonObject1.length() ; i++) {

                        }
                        adapterTask.notifyDataSetChanged();
                    } catch (Exception e) {

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("MaNV", employee.getId());
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

}