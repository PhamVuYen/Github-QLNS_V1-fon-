package com.example.qlnv.activity.timekeeping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.example.qlnv.activity.assigntask.UpdateStatusTaskActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SuccessActivity extends AppCompatActivity {
    TextView btnFinish,tvTittle;
    String idEmployee = "";
    String tittle = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        initView();
        tittle = getIntent().getStringExtra("timekeeping");
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuccessActivity.this, HomeActivity.class));
            }
        });
        tvTittle.setText(tittle);
        getTimeKeeping();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void getTimeKeeping() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_GET_TIME_RECORER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        Log.d("responseGet", response);
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() == 0) {
                            updateTimeRecord("1", Injector.getLateTime(Injector.getCurrentTime()));
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String NgayCong = jsonObject.getString("NgayCong");
                                String PhutDiMuon = jsonObject.getString("PhutDiMuon");
                                String ngaycongCheck = "";
                                if (tittle.equals(getResources().getString(R.string.checkin))) {
                                    ngaycongCheck = String.valueOf(Integer.parseInt(NgayCong) + 1) ;
                                } else {
                                    ngaycongCheck = String.valueOf(Integer.parseInt(NgayCong));
                                }
                                Log.d("ngaycongCheck",ngaycongCheck);
//                                updateTimeRecord(String.valueOf(ngaycongCheck),
//                                        String.valueOf(Integer.parseInt(PhutDiMuon) + Integer.parseInt(Injector.getLateTime(Injector.getCurrentTime()))));
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(SuccessActivity.this, "Some error", Toast.LENGTH_LONG).show();
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
                param.put("MaNV", Injector.getEmployee().getId());
                param.put("Thang", String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    void updateTimeRecord(String workDay, String lateMinute) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_ADD_TIME_RECORER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        Log.d("responseUpdate", response);
                    } catch (Exception e) {
                        Toast.makeText(SuccessActivity.this, "Some error", Toast.LENGTH_LONG).show();
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
                param.put("MaNV", Injector.getEmployee().getId());
                param.put("Thang", String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
                param.put("NgayCong", workDay);
                param.put("PhutDiMuon", lateMinute);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }



    private void initView() {
        btnFinish = findViewById(R.id.btnFinish);
        tvTittle = findViewById(R.id.tvTittle);
    }
}