package com.example.qlnv.activity.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.qlnv.R;
import com.example.qlnv.activity.HomeActivity;
import com.example.qlnv.activity.LoginActivity;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvChangePassword,tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        tvChangePassword = findViewById(R.id.btnChangePassword);
        tvLogout = findViewById(R.id.btnLogOut);
        tvChangePassword.setOnClickListener(this);
        tvLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnChangePassword:
                startActivity(new Intent(AccountActivity.this, ChangePasswordActivity.class));
            case R.id.btnLogOut:
                startActivity(new Intent(AccountActivity.this,LoginActivity.class));
        }
    }
}