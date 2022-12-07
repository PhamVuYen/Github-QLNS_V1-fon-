package com.example.qlnv.activity.assigntask;

import static java.sql.Types.NULL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.qlnv.R;
import com.example.qlnv.activity.HomeActivity;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class AssignTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView task_assigned_to_phone_number, task_assigned_id;
    private EditText task_assigned_name;
    private Button task_assigned_btn;
    private String p3_number;
    public String task_id_unique;
    private TextView tvHourEnd, tvDateEnd;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        p3_number = getIntent().getStringExtra("uid");
        task_assigned_btn = findViewById(R.id.task_assigned_btn);
        task_assigned_id = findViewById(R.id.task_assigned_id);
        tvHourEnd = findViewById(R.id.tvHourEnd);
        tvDateEnd = findViewById(R.id.tvDateEnd);
        spinner = findViewById(R.id.spinner1);


        task_id_unique = UUID.randomUUID().toString();
        task_assigned_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask();
            }
        });

        tvHourEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(
                        AssignTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvHourEnd.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                timePicker.show();
            }
        });

        tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(
                        AssignTaskActivity.this,
                        AssignTaskActivity.this,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    private void updateTask() {

        if (TextUtils.isEmpty(task_assigned_name.getText().toString())) {
            Toast.makeText(this, "Task name can't be empty", Toast.LENGTH_SHORT).show();
        } else {

//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tasks");
//
//            HashMap<String, Object> userMap = new HashMap<>();
//            userMap.put("task_id", task_id_unique);
//            userMap.put("task_name", task_assigned_name.getText().toString());
//            userMap.put("task_status", "NOT DONE");
//            userMap.put("reason", "NOT SPECIFIED");
//
//            ref.child(p3_number).child(task_id_unique).updateChildren(userMap);
            Toast.makeText(this, "Task assigned successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        dateButton.setText(dayOfMonth + "/" + month + "/" + year);
    }
}
