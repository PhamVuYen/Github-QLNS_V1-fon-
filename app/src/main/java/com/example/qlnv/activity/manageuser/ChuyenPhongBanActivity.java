package com.example.qlnv.activity.manageuser;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.qlnv.R;
import com.example.qlnv.model.Employee;
import com.example.qlnv.model.Room;


public class ChuyenPhongBanActivity extends Activity {

	ListView lvPb;
	private static ArrayList<Room> arrRoom =null;
	ArrayAdapter<Room> adapter;
	ImageButton btnApply;
	Employee nv=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chuyen_phong_ban);
		getFormWidgets();
		//lấy nhân viên từ màn hình xem danh sách nhân viên
		Intent i=getIntent();
		Bundle b= i.getBundleExtra("DATA");
		nv=(Employee) b.getSerializable("NHANVIEN");
	}
	/**
	 * hàm lấy control theo id
	 * đồng thời load toàn bộ danh sách phòng ban ở MainActivity
	 * lên ListView để sử dụng
	 * android.R.layout.simple_list_item_single_choice ->dùng Radio
	 * Bắt buộc phải xử lý hàm: lvPb.setOnItemClickListener
	 * để gán checked cho Radio
	 */
	public void getFormWidgets()
	{
		lvPb=(ListView) findViewById(R.id.lvphongban);
		btnApply=(ImageButton) findViewById(R.id.imgapply);
		
		arrRoom = ManageUserActivity.getListPhongBan();
		adapter=new ArrayAdapter<Room>
		(this, android.R.layout.simple_list_item_single_choice,
				arrRoom);
		lvPb.setAdapter(adapter);

		lvPb.setOnItemClickListener(new OnItemClickListener() {

			boolean somethingChecked = false;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//hiển nhiên View arg1 là CheckedTextView
				if(somethingChecked){
					CheckedTextView cv = (CheckedTextView) arg1;
					cv.setChecked(false);

				}
				CheckedTextView cv = (CheckedTextView) arg1;
				if(!cv.isChecked())
				{
					cv.setChecked(true);
					arrRoom.get(arg2).themNv(nv);
				}
				somethingChecked=true;
			}
		});
		//khi chọn nút Apply thì tiến hành đóng màn hình này
		//và truyền lệnh về cho DanhSachNhanVienACtivity
		btnApply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				doApply();

			}
		});
	}
	public void doApply()
	{
		setResult(ManageUserActivity.CHUYENPHONG_THANHCONG);
		finish();
	}
}
