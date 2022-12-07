package com.example.qlnv.activity;

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

import com.example.qlnv.Injector;
import com.example.qlnv.R;
import com.example.qlnv.activity.account.AccountActivity;
import com.example.qlnv.activity.assigntask.AssignTaskActivity;
import com.example.qlnv.activity.manageuser.ManageUserActivity;
import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Role;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    ConstraintLayout timeKeeping, manageUser, assignTask, myTask, account, summary;
    TextView tvUserName,tvRole;
    Employee employee;
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
                if (employee.getRole().equals(Role.Admin)) {
                    startActivity(new Intent(HomeActivity.this, ManageUserActivity.class));
                } else {
                    Toast.makeText(HomeActivity.this, "You don't have permission", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.assigntask:
                startActivity(new Intent(HomeActivity.this, AssignTaskActivity.class));
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


}