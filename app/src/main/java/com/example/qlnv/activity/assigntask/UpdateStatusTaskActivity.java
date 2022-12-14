package com.example.qlnv.activity.assigntask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.example.qlnv.activity.manageuser.EditEmployeeActivity;
import com.example.qlnv.activity.manageuser.ManageUserActivity;
import com.example.qlnv.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateStatusTaskActivity extends AppCompatActivity {
    private Spinner spinner;
    private Button btnUpdateTask;
    private String status = "";
    String idTask = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status_task);
        initView();
        idTask = getIntent().getStringExtra("idTask");
        ArrayList<String> items = new ArrayList<>();
        items.add("Đang làm");
        items.add("Đã hoàn thành");
        items.add("Quá hạn");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = (String) parent.getItemAtPosition(pos);
                status = (String) item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatusTask(idTask,status);
            }
        });

    }

    void updateStatusTask(String id,String status) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_UPDATE_STATUS_TASK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        Log.d("response",response);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(UpdateStatusTaskActivity.this, "Some error", Toast.LENGTH_LONG).show();
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
                param.put("MaCViec", id);
                param.put("Status",status);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    void initView() {
        spinner = findViewById(R.id.spinner1);
        btnUpdateTask = findViewById(R.id.update_task_status);
    }
}