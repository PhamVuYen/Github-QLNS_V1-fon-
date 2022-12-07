package com.example.qlnv.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlnv.Injector;
import com.example.qlnv.R;
import com.example.qlnv.SharedPref;
import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Role;
import com.example.qlnv.roomdb.UserDatabase;
import com.example.qlnv.roomdb.UserDatabaseClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialButton btnLogin;
    TextInputEditText edtTextUser, edtPassword;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPref sharedPref = SharedPref.getInstance();
        if (sharedPref.getUser(this) != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btnLogin.setOnClickListener(this);
    }

    public void initView() {
        btnLogin = findViewById(R.id.btnLogin);
        edtTextUser = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                getData();
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }

    void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Injector.URL_USER, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length() - 1; i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String msv = jsonObject.getString("MaNV");
                            String password = jsonObject.getString("Password");
                            if (edtTextUser.getText().toString().equals(msv) && edtPassword.getText().toString().equals(password)) {
                                String role = jsonObject.getString("ChucVu");
                                String name = jsonObject.getString("TenNV");
                                Employee employee = Injector.getEmployee();
                                employee.setIdentified(msv);
                                employee.setName(name);
                                employee.setPassword(password);
                                employee.setRole(Role.Admin);
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else {
//                                Toast.makeText(LoginActivity.this, "Fail to connect", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, "Fail to connect server employee", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error + "", Toast.LENGTH_LONG).show();
                Log.d("response1", error + "");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }


    class LoginUserTask extends AsyncTask<Void, Void, Void> {

        private final String username;
        private final String password;
        private ArrayList<Employee> users = new ArrayList<>();

        public LoginUserTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            users = (ArrayList<Employee>) databaseClient.userDao().observeAllUser();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (Employee user : users) {
                if (username.equals(user.getName()) && password.equals(user.getPassword())) {
                    SharedPref sharedPref = SharedPref.getInstance();
                    sharedPref.setUser(LoginActivity.this, user);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    return;
                }
            }
            Toast.makeText(LoginActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
        }
    }

}
