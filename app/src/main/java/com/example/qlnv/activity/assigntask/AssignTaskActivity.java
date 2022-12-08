package com.example.qlnv.activity.assigntask;

import static java.sql.Types.NULL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.qlnv.activity.HomeActivity;
import com.example.qlnv.activity.manageuser.AddEmployeeActivity;
import com.example.qlnv.activity.manageuser.ManageUserActivity;
import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;
import com.example.qlnv.model.Task;

import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AssignTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText edtTaskName;
    private Button task_assigned_btn;
    public String task_id_unique;
    private TextView tvHourEnd, tvDateEnd;
    private Spinner spinner;
    private String[] separated = new String[2];
    private Employee employee = null;
    private Room room = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        edtTaskName = findViewById(R.id.task_assigned_name);
        task_assigned_btn = findViewById(R.id.task_assigned_btn);
        tvHourEnd = findViewById(R.id.tvHourEnd);
        tvDateEnd = findViewById(R.id.tvDateEnd);
        spinner = findViewById(R.id.spinner1);

        employee = Injector.getEmployee();
        room = new Room();
        room.setId(employee.getIdRoom());
        getUserInRoom(room);
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

        String[] items = new String[room.dsnv.size()];
        for (int i=0;i<room.dsnv.size();i++) {
            items[i] = room.dsnv.get(i).getName() + "-" + room.dsnv.get(i).getId();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = (String) parent.getItemAtPosition(pos);
                separated = ((String) item).split("-");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getUserInRoom(Room room) {
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
                            Employee employee = new Employee(mnv,name,date,diachi,sex,phone,email,cmnd,chucvu,room.getId(),stk,luong);
                            room.dsnv.add(employee);
                        }

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
                param.put("MaPB", room.getId());
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }



    private void updateTask() {

        if (edtTaskName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Task name can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (tvDateEnd.getText().toString().isEmpty() || tvHourEnd.getText().toString().isEmpty()) {
                Toast.makeText(this, "You must select time end task", Toast.LENGTH_SHORT).show();
            } else {
                String time = tvDateEnd.getText().toString() +" " + tvHourEnd.getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date deadline = null;
                try {
                    deadline = df.parse(time);
                    Log.d("item",deadline+"||"+new Date());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Task task = new Task();
                task.setTask_id(task_id_unique);
                task.setTask_name(edtTaskName.getText().toString());
                task.setManage_id(Injector.getEmployee().getId());
                task.setCreateDay(new Date());
                task.setUser_id(separated[1]);
                task.setTask_status("Chưa xong");
                task.setDeadline(deadline);
                addTaskToDB(task);
            }

        }
    }

    private void addTaskToDB(Task task) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_ASSIGN_TASK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        Log.d("response", response);
                        Toast.makeText(AssignTaskActivity.this, "Task assigned successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err", error + "");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("MaCViec", task.getTask_id());
                param.put("TenCviec", task.getTask_name());
                param.put("DeadlineCV", Injector.datetoStringTime(task.getDeadline()));
                param.put("CreateDate", Injector.dateToString(task.getCreateDay()));
                param.put("MaNV", task.getUser_id());
                param.put("Status",task.getTask_status());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tvDateEnd.setText(dayOfMonth + "/" + month + "/" + year);
    }
}
