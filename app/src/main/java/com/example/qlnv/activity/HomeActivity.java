package com.example.qlnv.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.accounts.Account;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.example.qlnv.activity.account.AccountActivity;
import com.example.qlnv.activity.assigntask.AssignTaskActivity;
import com.example.qlnv.activity.manageuser.ManageUserActivity;
import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Role;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    ConstraintLayout timeKeeping, manageUser, assignTask, myTask, account, summary;
    TextView tvUserName,tvRole;
    Employee employee;
    private ArrayList<Employee> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        timeKeeping.setOnClickListener(this);
        manageUser.setOnClickListener(this);
        assignTask.setOnClickListener(this);
        myTask.setOnClickListener(this);
        account.setOnClickListener(this);
        summary.setOnClickListener(this);
        employee = Injector.getEmployee();
        tvUserName.setText(employee.getName() +"-"+employee.getIdentified());
        tvRole.setText("ADMIN");
    }

    void initView() {
        timeKeeping = findViewById(R.id.timekeeping);
        manageUser = findViewById(R.id.manageuser);
        assignTask = findViewById(R.id.assigntask);
        myTask = findViewById(R.id.mytask);
        account = findViewById(R.id.account);
        summary = findViewById(R.id.summary);
        tvUserName = findViewById(R.id.textView2);
        tvRole = findViewById(R.id.tvRole);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.timekeeping:
                break;
            case R.id.manageuser:
                if (employee.getRole().equals("ADMIN")) {
                    startActivity(new Intent(HomeActivity.this, ManageUserActivity.class));
                } else {
                    Toast.makeText(HomeActivity.this, "You don't have permission", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.assigntask:
                if (employee.getRole().equals("Trưởng phòng")) {
                    getUserInRoom(employee.getIdRoom());
                } else {
                    Toast.makeText(HomeActivity.this, "You don't have permission", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.mytask:
                break;
            case R.id.account:
                startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                break;
            case R.id.summary:
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(
                        HomeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.d("hour",hourOfDay + ":" + minute);
                    }
                },hour,minute,true);
                timePicker.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
        System.exit(0);  // exit app
    }
    private void getUserInRoom(String idroom) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_QUERY_USER_ROOM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        for (int i = 0 ; i < jsonObject1.length() ; i++) {
                            JSONObject jsonObject = jsonObject1.getJSONObject(String.valueOf(i));
                            String mnv = jsonObject.getString("MaNV");
                            String name = jsonObject.getString("TenNV");
                            Date date = new Date();
                            String diachi = jsonObject.getString("DiaChi");
                            String gioitinh = jsonObject.getString("GioiTinh");
                            String phone = jsonObject.getString("Phone");
                            String email = jsonObject.getString("Email");
                            String cmnd = jsonObject.getString("SoCMND");
                            String stk = jsonObject.getString("SoTk");
                            String luong = jsonObject.getString("MucLuong");
                            String chucvu = jsonObject.getString("ChucVu");
                            Boolean sex = false;
                            if (gioitinh.equals("nam")) {
                                sex = true;
                            }
                            Employee employee = new Employee(mnv,name,date,diachi,sex,phone,email,cmnd,chucvu,idroom,stk,luong);
                            arrayList.add(employee);
                        }
                        Intent intent = new Intent(HomeActivity.this, AssignTaskActivity.class);
                        intent.putExtra("listuser",arrayList);
                        startActivity(intent);
                    Log.d("responseUser",response);
                    } catch (Exception e) {
//                        Toast.makeText(ManageUserActivity.this, "Fail to connect server employee in room", Toast.LENGTH_LONG).show();
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
                param.put("MaPB", idroom);
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }


}