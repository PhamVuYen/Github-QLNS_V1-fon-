package com.example.qlnv.activity.manageuser;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.example.qlnv.R;
import com.example.qlnv.model.Role;
import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;

import java.util.ArrayList;

/**
 * màn hình sẽ hiển thị danh sách nhân viên vào 2 ListView khác nhau
 * ListView 1 dùng Radio để chọn trưởng phòng
 * ListView 2 dùng Checkbox để chọn phó phòng
 *
 * @author drthanh
 */
public class SetManagerActivity extends Activity {

    ListView lvManager, lvSubManager;
    ArrayList<Employee> arrNvForTP = new ArrayList<Employee>();
    ArrayList<Employee> arrNvForPP = new ArrayList<Employee>();
    ArrayAdapter<Employee> adapterForTP;
    ArrayAdapter<Employee> adapterForPP;
    TextView btnApply;
    int lastChecked = -1;
    Room pb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thiet_lap_truong_phong);
        getFormWidgets();
    }


    public void getFormWidgets() {
        lvManager = (ListView) findViewById(R.id.lvtruongphong);
        lvSubManager = findViewById(R.id.lvphophong);

        lvManager.setTextFilterEnabled(true);
        lvManager.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvManager.setOnItemClickListener(new OnItemClickListener() {
            boolean somethingChecked = false;

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arrNvForTP.get(arg2).setRole(Role.Manager);
                if (somethingChecked) {
                    CheckedTextView cv = (CheckedTextView) arg1;
                    cv.setChecked(false);
                }
                CheckedTextView cv = (CheckedTextView) arg1;
                if (!cv.isChecked()) {
                    cv.setChecked(true);
                    arrNvForTP.get(arg2).setRole(Role.Manager);
                } else {
                    arrNvForTP.get(arg2).setRole(Role.Employee);
                }
                lastChecked = arg2;
                somethingChecked = true;
            }

        });

        adapterForTP = new ArrayAdapter<Employee>(this, android.R.layout.simple_list_item_single_choice, arrNvForTP);
        lvManager.setAdapter(adapterForTP);

        adapterForPP = new ArrayAdapter<Employee>(this, android.R.layout.simple_list_item_multiple_choice, arrNvForPP);
        lvSubManager.setAdapter(adapterForPP);
        lvSubManager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView cv = (CheckedTextView) view;
                if (!cv.isChecked()) {
                    cv.setChecked(true);
                    arrNvForPP.get(i).setRole(Role.SubManager);
                } else {
                    cv.setChecked(false);
                    arrNvForPP.get(i).setRole(Role.Employee);
                }
            }
        });


        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("DATA");
        pb = (Room) bundle.getSerializable("PHONGBAN");
        addNvToListTP(pb);
        addNvToListPP(pb);
        adapterForTP.notifyDataSetChanged();
        adapterForPP.notifyDataSetChanged();
        btnApply = (TextView) findViewById(R.id.imgapply);
        btnApply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                doApply();

            }
        });
    }

    public void doApply() {
        Intent i = getIntent();
        Bundle bundle = new Bundle();
        for (int index = 0; index < pb.dsnv.size(); index++) {
            Employee employee = pb.dsnv.get(index);
            if (index == lastChecked) {
                employee.setRole(Role.Manager);
            } else {
                employee.setRole(Role.Employee);
            }
            pb.dsnv.set(index, employee);
        }

        bundle.putSerializable("PHONGBAN", pb);
        i.putExtra("DATA", bundle);
        setResult(ManageUserActivity.THIET_LAP_TP_PP_THANHCONG, i);
        finish();
    }

    public void addNvToListTP(Room pb) {
        arrNvForTP.clear();
        for (Employee nv : pb.getListNhanVien()) {
            arrNvForTP.add(nv);
        }
    }

    public void addNvToListPP(Room pb) {
        arrNvForPP.clear();
        for (Employee nv : pb.getListNhanVien()) {
            arrNvForPP.add(nv);
        }
    }

}
