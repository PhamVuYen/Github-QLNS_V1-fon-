package com.example.qlnv.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlnv.R;
import com.example.qlnv.model.Employee;


public class EmployeeAdapter extends ArrayAdapter<Employee> {

	Activity context;
	int layoutId;
	ArrayList<Employee> arrNhanVien;
	public EmployeeAdapter(Activity context, int textViewResourceId,
						   ArrayList<Employee> objects) {
		super(context, textViewResourceId, objects);
		this.context=context;
		this.layoutId=textViewResourceId;
		this.arrNhanVien=objects;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=context.getLayoutInflater().inflate(layoutId, null);
		TextView txtnv= (TextView) convertView.findViewById(R.id.txtShortInfor);
		TextView txtmotanv= (TextView) convertView.findViewById(R.id.txtDetailInfor);
		ImageView img=(ImageView) convertView.findViewById(R.id.imgview);
		Employee nv=arrNhanVien.get(position);
		txtnv.setText(nv.toString());
		String strMota="";
		String cv = "Chức vụ: "+nv.getRole().getRole();
		String gt = "Giới tính: "+(nv.isSex()?"Nữ":"Nam");
		img.setImageResource(R.drawable.ic_girl);
		if(!nv.isSex())
			img.setImageResource(R.drawable.ic_boy);
		strMota=cv+"\n"+gt;
		txtmotanv.setText(strMota);
		return convertView;
	}

}
