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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void initView() {
        btnFinish = findViewById(R.id.btnFinish);
        tvTittle = findViewById(R.id.tvTittle);
    }
}