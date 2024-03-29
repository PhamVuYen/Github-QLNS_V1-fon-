package com.example.qlnv.activity.manageuser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qlnv.Injector;
import com.example.qlnv.R;
import com.example.qlnv.activity.HomeActivity;
import com.example.qlnv.activity.LoginActivity;
import com.example.qlnv.adapter.RoomAdapter;
import com.example.qlnv.model.Role;
import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;

import org.json.JSONArray;
import org.json.JSONObject;

public class ManageUserActivity extends Activity {

    public static final int MO_ACTIVITY_THEM_NHAN_VIEN = 1;
    public static final int MO_ACTIVITY_SUA_NHAN_VIEN = 2;
    public static final int THEM_NHAN_VIEN_THANHCONG = 3;
    public static final int SUA_NHAN_VIEN_THANHCONG = 4;
    public static final int XEM_DS_NHAN_VIEN = 5;
    public static final int CAPNHAT_DS_NHAN_VIEN_THANHCONG = 6;
    public static final int MO_ACTIVITY_THIET_LAP_TP_PP = 7;
    public static final int THIET_LAP_TP_PP_THANHCONG = 8;
    public static final int MO_ACTIVITY_CHUYENPHONG = 9;
    public static final int CHUYENPHONG_THANHCONG = 10;

    private TextView btnLuuPb;
    private EditText editMapb, editTenpb;
    private ListView lvpb;
    private static ArrayList<Room> arrRoom = new ArrayList<>();
    private RoomAdapter adapterPb = null;
    private Room pbSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        getFormWidgets();
        addEvents();
//        getDataRoom();
    }

    @Override
    protected void onStart() {
        super.onStart();
        arrRoom.clear();
        adapterPb.notifyDataSetChanged();
        getDataRoom();
    }

    void getDataRoom() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Injector.URL_ROOM, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String idRoom = jsonObject.getString("MaPB");
                            String nameRoom = jsonObject.getString("TenPB");
                            Room room = new Room();
                            room.setId(idRoom);
                            room.setName(nameRoom);
                            //room.dsnv
                            arrRoom.add(room);
                            getUserInRoom(room);
//                            adapterPb.notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(ManageUserActivity.this, "Some error", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error+"");
                Toast.makeText(ManageUserActivity.this, error + "Can't connect server employee", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
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

                            Role role = Role.Employee;
                            Employee employee = new Employee(mnv,name,date,diachi,sex,phone,email,cmnd,role,room.getId(),stk,luong);
                            room.dsnv.add(employee);
                            adapterPb.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                        Toast.makeText(ManageUserActivity.this, "Fail to connect server employee in room", Toast.LENGTH_LONG).show();
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
                Log.d("MaPB",param.size()+"");
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }



    public void getFormWidgets() {
        btnLuuPb = (TextView) findViewById(R.id.btnluupb);
        editMapb = (EditText) findViewById(R.id.editmapb);
        editTenpb = (EditText) findViewById(R.id.editTenpb);
        lvpb = (ListView) findViewById(R.id.lvphongban);
        adapterPb = new RoomAdapter(this,
                R.layout.layout_item_pb,
                arrRoom);
        lvpb.setAdapter(adapterPb);
        registerForContextMenu(lvpb);
    }

    public void addEvents() {
        btnLuuPb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    doLuuPhongBan();
                }
                editTenpb.clearFocus();
                editMapb.clearFocus();
                editTenpb.setText("");
                editMapb.setText("");
            }
        });
        lvpb.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                pbSelected = arrRoom.get(arg2);
                Log.d("idRoom", pbSelected.getId() + "");
                return false;
            }

        });
    }

    void addRoom(Room room) {
        Log.d("ROOM",room.getId());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Injector.URL_ADD_ROOM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Log.d("responseAddRoom",response+"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error+"");
                Toast.makeText(ManageUserActivity.this, error + "Can't connect server employee", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("TenPB",room.getName());
                param.put("MaPB",room.getId());
                Log.d("employee",room.getId());
                return param;
            }
        };;

        requestQueue.add(stringRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void doLuuPhongBan() {
        ArrayList<Room> arrRoom = new ArrayList<>();
        String mapb = editMapb.getText() + "";
        String tenpb = editTenpb.getText() + "";
        Log.d("mapb",editMapb.getText() + "abc");
        arrRoom = adapterPb.getArrPhongBan();

//        boolean alreadyHasId =
//                arrRoom
//                        .stream()
//                        .anyMatch(m -> m.getId().equals(mapb) || m.getName().equals(tenpb));
        boolean alreadyHasId = true;

        if (alreadyHasId) {
            Room pb = new Room(mapb, tenpb);
            arrRoom.add(pb);
            addRoom(pb);
            adapterPb.notifyDataSetChanged();
            Toast.makeText(ManageUserActivity.this, "Tạo phòng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ManageUserActivity.this, "Phòng đã tồn tại", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu_phongban, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuthemnv:
                doThemNhanVien();
                break;
            case R.id.mnudanhsachnv:
                doDanhSachNhanVien();
                break;
            case R.id.mnutruongphong:
                doThietLapLanhDao();
                break;
            case R.id.mnuxoapb:
                doXoaPhongBan();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void doThemNhanVien() {
        Intent i = new Intent(this, AddEmployeeActivity.class);
        i.putExtra("idRoom", pbSelected.getId());
        startActivityForResult(i, MO_ACTIVITY_THEM_NHAN_VIEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //màn hình thêm mới nhân viên trả kết quả về
        if (resultCode == THEM_NHAN_VIEN_THANHCONG) {
            Bundle bundle = data.getBundleExtra("DATA");
            Employee nv = (Employee) bundle.getSerializable("NHANVIEN");
//            adapterPb.arrRoom.clear();
//            getDataRoom();
        }
        //Màn hình thiết lập lạnh đão/ cập nhật danh sách trả
        //kết quả về
        else if (resultCode == THIET_LAP_TP_PP_THANHCONG ||
                resultCode == CAPNHAT_DS_NHAN_VIEN_THANHCONG) {

            Bundle bundle = data.getBundleExtra("DATA");
            Room pb = (Room) bundle.getSerializable("PHONGBAN");
            //đơn thuần là xóa danh sách cũ
            pbSelected.getListNhanVien().clear();
            //rồi cập nhật lại toàn bộ danh sách mới
            //sinh viên có thể chọn giải pháp thông minh hơn
            //tức là phần tử nào bị ảnh hưởng thì xử lý phần tử đó
            pbSelected.getListNhanVien().addAll(pb.getListNhanVien());
            adapterPb.notifyDataSetChanged();
        }
    }


    public void doDanhSachNhanVien() {
        Intent i = new Intent(this, EmployeeListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PHONGBAN", pbSelected);
        i.putExtra("DATA", bundle);
        startActivityForResult(i, XEM_DS_NHAN_VIEN);
    }

    public void doThietLapLanhDao() {
        Intent i = new Intent(this, SetManagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PHONGBAN", pbSelected);
        i.putExtra("DATA", bundle);
        startActivityForResult(i, MO_ACTIVITY_THIET_LAP_TP_PP);
    }

    public void doXoaPhongBan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hỏi xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa [" + pbSelected.getName() + "]");
        builder.setIcon(android.R.drawable.ic_input_delete);
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                arg0.cancel();
            }
        });
        builder.setPositiveButton("Ừ", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                deleteRoomToDB(pbSelected);
                arrRoom.remove(pbSelected);
                adapterPb.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    private void deleteRoomToDB(Room room) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Injector.URL_DEL_ROOM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        Log.d("response",response);

                    } catch (Exception e) {
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("err",error+"");
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

    public static ArrayList<Room> getListPhongBan() {
        return arrRoom;
    }
}
