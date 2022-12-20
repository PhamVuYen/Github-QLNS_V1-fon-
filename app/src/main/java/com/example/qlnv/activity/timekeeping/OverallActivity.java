package com.example.qlnv.activity.timekeeping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qlnv.R;

public class OverallActivity extends AppCompatActivity {
    TextView tvWorkDay,tvLateDay,tvLateMinute,tvFine,tvSalary;
    Button btnDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall);
        initView();
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OverallActivity.this,TimeKeepingDetailActivity.class));
            }
        });
    }

    private void initView() {
        tvWorkDay = findViewById(R.id.tvWorkDay);
        tvLateDay = findViewById(R.id.tvLateDay);
        tvLateMinute = findViewById(R.id.tvLateMinute);
        tvFine = findViewById(R.id.tvFine);
        tvSalary = findViewById(R.id.tvSalary);
        btnDetail = findViewById(R.id.btnDetail);
    }
}