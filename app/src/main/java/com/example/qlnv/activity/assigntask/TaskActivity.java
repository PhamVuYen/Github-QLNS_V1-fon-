package com.example.qlnv.activity.assigntask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.example.qlnv.OnClickListener;
import com.example.qlnv.R;
import com.example.qlnv.activity.manageuser.ManageUserActivity;
import com.example.qlnv.adapter.AdapterTask;

import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;
import com.example.qlnv.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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
        tasks.add(new Task("abcd","abcd","acbd","abcd","abcd",new Date(),new Date()));
        adapterTask = new AdapterTask(tasks, TaskActivity.this);
        adapterTask.setOnClickListener(new OnClickListener() {
            @Override
            public void onItemLongClick(String id) {
                Intent i = new Intent(TaskActivity.this,UpdateStatusTaskActivity.class);
                i.putExtra("idTask",id);
                startActivity(i);
            }
        });
        rvTask.setAdapter(adapterTask);
        rvTask.setLayoutManager(new LinearLayoutManager(TaskActivity.this));
        getTaskOfUser(employee);
    }


    void initView() {
        rvTask = findViewById(R.id.rv_task);
    }

    private void getTaskOfUser(Employee employee) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_QUERY_TASK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonArray = new JSONObject(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(String.valueOf(i));
                            String TenCViec = jsonObject.getString("TenCViec");
                            String MaCViec = jsonObject.getString("MaCViec");
                            String userid = jsonObject.getString("MaNV");
//                        Date DealineCV = (Date) jsonObject.get("DealineCV");
                            String Status = jsonObject.getString("Status");
                            tasks.add(new Task(userid,MaCViec,TenCViec,Status,"abcd",new Date(),new Date()));
                            adapterTask.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error + "");
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